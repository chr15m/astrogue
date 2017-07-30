(ns ld39.core
    (:require [reagent.core :as reagent]
              [ld39.engine :refer [build-map wait-for-win!]]
              [ld39.renderer :refer [component-renderer resize-screen!]]))

(defn mount-root []
  (let [app-element (.getElementById js/document "app")
        game-state (reagent/atom {:window [0 0]})
        clock (reagent/atom 0)
        display (js/ROT.Display.)
        [game-map boxes player npc] (build-map)
        dimensions [80 25]
        handle-resize-event! (partial resize-screen! (reagent/cursor game-state [:window]) dimensions)]
    (swap! game-state assoc :player player)
    (swap! game-state assoc :npc npc)
    (handle-resize-event!)
    (.addEventListener js/window "resize" handle-resize-event!)
    (js/setInterval (fn [] (reset! clock (.getTime (js/Date.)))) 100)
    (reagent/render [component-renderer dimensions game-state game-map boxes clock] (.getElementById js/document "app"))
    ;(set! (.-innerHTML app-element) "")
    ;(.appendChild app-element (.getContainer display))
    (wait-for-win! display game-map boxes game-state)))

(defn init! []
  (mount-root))
