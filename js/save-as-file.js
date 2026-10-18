document.getElementById('save-button').addEventListener('click', async () => {
  const text = document.getElementById('post').value;

  const blob = new Blob([text], { type: 'text/plain;charset=utf-8' });
  const filename = 'заголовок.txt';

  // iOS Safari: use Web Share API
  const isIOS = /iPhone|iPad|iPod/.test(navigator.userAgent) ||
                (navigator.maxTouchPoints > 2 && /Macintosh/.test(navigator.userAgent));

  if (isIOS && navigator.canShare) {
    try {
      const file = new File([blob], filename, { type: 'text/plain' });
      if (navigator.canShare({ files: [file] })) {
        await navigator.share({ files: [file], title: filename });
        return;
      }
    } catch (e) {
      if (e.name === 'AbortError') return;
    }
  }

  // Desktop (including Safari): attach, click, then defer revoke
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = filename;
  a.style.display = 'none';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);

  // KEY FIX: don't revoke immediately — give Safari time to start the download
  setTimeout(() => URL.revokeObjectURL(url), 40000);
});