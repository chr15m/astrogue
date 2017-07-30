LIBDIR=public/js/lib/
LIBS=webtorrent.min.js bencode-min.js rot.min.js

downloads: $(addprefix $(LIBDIR)/, $(LIBS))

$(LIBDIR)/webtorrent.min.js:
	curl -s https://cdn.jsdelivr.net/webtorrent/0.81.2/webtorrent.min.js > $@

$(LIBDIR)/bencode-min.js:
	curl -s https://raw.githubusercontent.com/benjreinhart/bencode-js/ccc5353/bencode-min.js > $@

$(LIBDIR)/rot.min.js:
	curl -s https://raw.githubusercontent.com/ondras/rot.js/94573bf/rot.min.js > $@

