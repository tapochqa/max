plist:
	launchctl unload ~/Library/LaunchAgents/ru.limonadny.max.plist
	launchctl load ~/Library/LaunchAgents/ru.limonadny.max.plist

cp:
	git add .
	@read -p "Commit message: " m; \
	git commit -m "$$m"
	git push --all gh

tests:
	lein test

run:
	lein run

run-bot:
	lein run -m teabot.core/-main

push:
	git push --all hs
	git push --all gh


setup:
	git remote rename origin hs
	git remote add gh https://github.com/tapochqa/max.git
	touch creds
	touch env

bundle:
	lein install

daemon-restart:
	launchctl kill -5 gui/501/ru.limonadny.max

pull:
	git pull origin master

upd: pull daemon-restart

creds := $(shell git remote get-url micro)

deploy-micro:
	ssh ${creds} 'cd ~/deploy/max && git pull origin master && killall java'

cpd:
	@read -p "Commit message: " m; \
	make tests; \
	git add .; \
	git commit -m "$$m"
	git push --all gh

	make deploy-micro

d: test deploy-micro


cpdnt: cp deploy-micro