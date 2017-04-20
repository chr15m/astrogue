(ns astrogue.core
    (:require [reagent.core :as reagent]
              [astrogue.engine :refer [build-map wait-for-win!]]))

(defn mount-root []
  ;(reagent/render [current-page] (.getElementById js/document "app"))
  (let [app-element (.getElementById js/document "app")
        game-state (reagent/atom {})
        display (js/ROT.Display.)
        [game-map boxes player npc] (build-map)]
    (swap! game-state assoc :player player)
    (swap! game-state assoc :npc npc)
    (set! (.-innerHTML app-element) "")
    (.appendChild app-element (.getContainer display))
    (wait-for-win! display game-map boxes game-state)))

(defn init! []
  (mount-root))
