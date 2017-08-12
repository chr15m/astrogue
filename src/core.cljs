(ns astrogue.core
  (:require [reagent.core :as reagent]
            [clojure.core.async :refer [<!]]
            [astrogue.engine :refer [build-map wait-for-win!]]
            [astrogue.renderer :refer [component-renderer resize-screen!]]
            [astrogue.yaba :as yaba]
            [astrogue.multiplayer :as multiplayer])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

;(print (yaba/encode 0xfffff))
;(print (yaba/encode 0xfffff))
;(print (yaba/decode "vuxijageca"))

;(print (yaba/encode (bit-and 0x00ed64c98 0x0000ffff)))
;(print (yaba/encode (bit-shift-right 0x00ed64c98 16)))

;(print (+ (yaba/decode "lifica") (bit-shift-left (yaba/decode "zope") 16)))
; 248925336
; 0xed64c98

(defn room-to-seed [r]
  (let [[m l] (clojure.string/split r " ")]
    (+ (yaba/decode m) (bit-shift-left (yaba/decode l) 16))))

(defn seed-to-room [s]
  (print "seed-to-room" s)
  (str (yaba/encode (bit-and s 0x0000ffff)) " " (yaba/encode (bit-shift-right (bit-and s 0xffff0000) 16))))

(print (seed-to-room 248925336))

(defn mount-root []
  (let [url-hash (.. js/document -location -hash)
        url-hash (if (= url-hash "") "lifica zope" (.replace url-hash "#" ""))
        dungeon-seed (room-to-seed url-hash)
        app-element (.getElementById js/document "app")
        game-state (reagent/atom {:window [0 0]})
        clock (reagent/atom 0)
        rng (js/ROT.RNG.setSeed dungeon-seed)
        display (js/ROT.Display.)
        [game-map boxes player npc] (build-map)
        dimensions [80 25]
        handle-resize-event! (partial resize-screen! (reagent/cursor game-state [:window]) dimensions)
        ;wt (js/WebTorrent.)
        ;room (multiplayer/connect wt "room-one")
        next-dungeon (.getUniformInt js/ROT.RNG 0 0xffffffff)
        ]
    (print "dungeon-seeds" url-hash dungeon-seed)
    (print "next" (seed-to-room next-dungeon) next-dungeon)
    ;(def ROOM room)
    ;(def WT wt)
    (swap! game-state assoc :player player)
    (swap! game-state assoc :npc npc)
    (comment (go-loop []
             (let [x (<! (room :chan))]
               (println "Got a value in this loop:" x)
               (if x
                 (recur)))))
    (handle-resize-event!)
    (.addEventListener js/window "resize" handle-resize-event!)
    (js/setInterval (fn [] (reset! clock (.getTime (js/Date.)))) 100)
    (reagent/render [component-renderer dimensions game-state game-map boxes clock] (.getElementById js/document "app"))
    ;(set! (.-innerHTML app-element) "")
    ;(.appendChild app-element (.getContainer display))
    (wait-for-win! display game-map boxes game-state)))

(defn init! []
  (mount-root))
