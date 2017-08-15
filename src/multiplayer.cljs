(ns astrogue.multiplayer
  (:require [clojure.core.async :refer [put! close! chan]]
            [cljsjs.nacl-fast :as nacl]))

(def EXT "xx_astrogue")

(defn attach-extension-protocol [wire addr c]
  (let [t (fn [wire]
            (print "Created extension protocol."))]
    ; yuck javascript
    (set! (.. t -prototype -name) EXT)
    (set! (.. t -prototype -onExtendedHandshake)
          (fn [handshake]
            (js/console.log "Extended handshake" handshake)
            (if (and (.-m handshake) (aget (.-m handshake) EXT))
              (js/console.log "Got wire" wire))))
    (set! (.. t -prototype -onMessage)
          (fn [message]
            ;(js/console.log "Message")
            ;(js/console.log message)
            (let [decoded (.decode js/Bencode (.toString message))]
              (js/console.log "Message:" decoded)
              ; TODO: verify sig before passing on
              ; nacl.sign.detached.verify
              (put! c ["message" (js->clj decoded)]))))
    t))

(defn connect [wt channel-name]
  (let [content (js/Blob. #js [0])
        c (chan)]
    (set! (.. content -name) channel-name)
    (.on wt "torrent"
         (fn [torrent] (js/console.log "torrent" torrent)))
    (let [torrent (.seed wt content (fn [torrent] (js/console.log "seeding" torrent)))]
      (.on torrent "wire" (fn [wire addr]
                            (js/console.log "saw wire" (.-peerId wire))
                            (put! c ["connect" wire])
                            (.use wire (attach-extension-protocol wire addr c))
                            (.on wire "close"
                                 (fn []
                                   (put! c ["close" wire])
                                   (js/console.log "closed" (.-peerId wire))))))
      {:channel-name channel-name :torrent torrent :chan c})))

(defn disconnect [wt channel-struct]
  (.remove wt (channel-struct :torrent))
  (close! (channel-struct :chan)))

(defn set-room [old-channel-struct wt room-name]
  (if (and old-channel-struct (= (old-channel-struct :channel-name) room-name))
    (do
      (print "Already in room:" room-name)
      old-channel-struct)
    (do
      (when old-channel-struct
        (print "Leaving room:" (old-channel-struct :channel-name))
        (disconnect wt old-channel-struct))
      (print "Joining room:" room-name)
      (connect wt room-name))))

(defn post [channel-struct message secret-key]
  (let [uid (js/Array.from (nacl.randomBytes 32))
        message (assoc message "uid" uid)
        bencoded-message (-> (js/TextEncoder. "utf8") (.encode (.encode js/Bencode message)))
        signature (js/Array.from (nacl.sign.detached bencoded-message (js/Uint8Array.from secret-key)))
        message (assoc message "sig" signature)]
    (put! (channel-struct :chan) ["message" (js->clj (clj->js message))])
    (doseq [w (.. (channel-struct :torrent) -wires)]
      (.extended w EXT (clj->js message)))))

(defn make-profile []
  {:name ""
   :secret-key (js/Array.from (.-secretKey (.keyPair nacl.sign)))})

(defn keys-from-secret [secret]
  (.fromSecretKey nacl.sign.keyPair (js/Uint8Array.from secret)))

(defn pk-from-secret [secret]
  (js/Array.from (.-publicKey (keys-from-secret secret))))
