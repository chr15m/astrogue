(ns astrogue.multiplayer
  (:require [clojure.core.async :refer [put! close! chan]]))

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
            (js/console.log "Message")
            (js/console.log message)
            (let [decoded (.decode js/Bencode (.toString message))]
              (js/console.log decoded)
              (put! c ["message"  (js->clj decoded)]))))
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
      {:torrent torrent :chan c})))

(defn disconnect [wt channel-struct]
  (.remove wt (channel-struct :torrent))
  (close! (channel-struct :chan)))

(defn post [channel-struct message]
  (for [w  (.. (channel-struct :torrent) -wires)]
    (.extended w EXT (clj->js message))))

