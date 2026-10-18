(ns max.text-processor
  (:require
   [max.helper :as helper]
   [hiccup2.core :as hiccup]
   
   [clojure.string :as str]
   [clojure.java.io :as io])
  
  (:import java.text.SimpleDateFormat
   java.util.Date
   java.util.Base64
   java.text.Collator))


(defn format-date [date-string]
  (let [date-format (SimpleDateFormat. "dd.MM.yyyy")]
    (.parse date-format date-string)))


(defn parse-date
  "finds a stamp like 19.04.1999 in text and returns its unix"
  [content]
  (let [date
        (re-find
          #"[0-3][0-9]\.[0-1][0-9]\.[0-2][0-9][0-9][0-9]"
          content)
        
        date
        (if date 
          (format-date date)
          (format-date "01.01.1970"))
        
        unix
        (.getTime date)]
    unix))


(defn path->filename
  [path]
  (-> (str/split (str path) #"/")
       last))


(defn path->file-title
  [path]
  (-> (path->filename path)
       (str/split #"\.")
       first
       (java.net.URLDecoder/decode "UTF-8")))


(defn compile-images
  [content]
  (let [img-regexp #"/img (.*)\n"]
    (str/replace 
      content 
      img-regexp
      (fn [s]
        (format "<img width='100&percnt;' src='%s'></img>"
          (second
            (re-find img-regexp (first s))))))))


(defn compile-post
  [content]
  (let
    
    [title
     (path->file-title content)
     
     title
     (str/replace title #"\%20" " ")
     
     content
     (slurp content)
     
     content
	 (compile-images content)
     
     content
     (str/trim content)
     
     content
     (str/replace content #"\n\n" "</p><p>")
     
     content
     (str/replace content #"\n" "<br/>")
     
     content
     (str/replace
       content
       #"(?U)\b[\p{L}']{1,2}\b\ "
       (fn [s] (str/replace s " " "&nbsp;")))
     
     content
     (str/replace
       content
       " —"
       "&nbsp;—")
     
     content
     (str/replace 
       content
       #"(?i)\b((?:https?:(?:/{1,3}|[a-zа-я0-9%])|[a-zа-я0-9.\-]+[.](?:рф|com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|dd|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)/)(?:[^\s()<>{}\[\]]+|\([^\s()]*?\([^\s()]+\)[^\s()]*?\)|\([^\s]+?\))+(?:\([^\s()]*?\([^\s()]+\)[^\s()]*?\)|\([^\s]+?\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’])|(?:(?<!@)[a-zа-я0-9]+(?:[.\-][a-zа-я0-9]+)*[.](?:рф|com|net|org|edu|gov|mil|aero|asia|biz|cat|coop|info|int|jobs|mobi|museum|name|post|pro|tel|travel|xxx|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cs|cu|cv|cx|cy|cz|dd|de|dj|dk|dm|do|dz|ec|ee|eg|eh|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|Ja|sk|sl|sm|sn|so|sr|ss|st|su|sv|sx|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)\b/?(?!@)))"
       (fn [s]
         (if 
           (re-find 
             #"http://|https://"
             (first s))
           (format 
             "<a href='%s'>%s</a>"
             (first s)  
             (first s))
           (format 
             "<a href='http://%s'>%s</a>"
             (first s)  
             (first s)))))
     
     content
     (str 
       "<div>"
       "<br><br>"
       "<h4>" title "</h4>"
       
       "<p>" content "</p>"
       
       "</div>")]
    
    content))


(defn index
  [path]
  (let 
    [seq
     (file-seq (io/file path))
     
     seq
     (rest seq)
     
     seq
     (filter 
       (fn [x] (->> x
                    str
                    (re-find #"\.txt")))
       seq)
     
     sorted
     (map 
       (fn [f]
         {:file f
          :unix (parse-date (slurp f))})
       seq)
            
     sorted
     (reverse (sort-by :unix sorted))]

    (map (fn [x] (compile-post (:file x))) sorted)))













