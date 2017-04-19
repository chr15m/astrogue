resources/public/lib/rot.min.js: resources/public/lib
	wget https://raw.githubusercontent.com/ondras/rot.js/94573bfa095530cdbb3e1026de7415818ccb5570/rot.min.js -O $@

resources/public/lib:
	mkdir -p resources/public/lib

