(ns astrogue.core
  (:require [reagent.core :as reagent]
            [clojure.core.async :refer [<!]]
            [astrogue.engine :refer [build-map wait-for-win!]]
            [astrogue.renderer :refer [component-renderer resize-screen!]]
            [astrogue.multiplayer :as multiplayer])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defn mount-root []
  (let [app-element (.getElementById js/document "app")
        game-state (reagent/atom {:window [0 0]})
        clock (reagent/atom 0)
        display (js/ROT.Display.)
        [game-map boxes player npc] (build-map)
        dimensions [80 25]
        handle-resize-event! (partial resize-screen! (reagent/cursor game-state [:window]) dimensions)
        wt (js/WebTorrent.)
        room (multiplayer/connect wt "room-one")]
    (def ROOM room)
    (def WT wt)
    (swap! game-state assoc :player player)
    (swap! game-state assoc :npc npc)
    (go-loop []
             (let [x (<! (room :chan))]
               (println "Got a value in this loop:" x)
               (if x
                 (recur))))
    (handle-resize-event!)
    (.addEventListener js/window "resize" handle-resize-event!)
    (js/setInterval (fn [] (reset! clock (.getTime (js/Date.)))) 100)
    (reagent/render [component-renderer dimensions game-state game-map boxes clock] (.getElementById js/document "app"))
    ;(set! (.-innerHTML app-element) "")
    ;(.appendChild app-element (.getContainer display))
    (wait-for-win! display game-map boxes game-state)))

(defn init! []
  (mount-root))
