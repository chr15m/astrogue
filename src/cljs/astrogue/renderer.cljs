(ns astrogue.renderer)

(defn rectangle-in-rectangle [r1 r2]
  (let [scale (js/Math.min (/ (first r2) (first r1)) (/ (last r2) (last r1)))]
    (vec (map #(int (* % scale)) r1))))

(defn resize-screen! [c dimensions]
  (reset! c
          (rectangle-in-rectangle dimensions [(.-innerWidth js/window) (.-innerHeight js/window)])))

(defn component-renderer [dimensions db game-map boxes]
  (let [player (@db :player)
        npc (@db :npc)]
    [:div
     [:svg#canvas {:width (get-in @db [:window 0])
                   :height (get-in @db [:window 1])
                   :viewBox (str "0 0 " (first dimensions) " " (last dimensions))}
      (doall
        (for [[[x y] tile] game-map]
          [:rect {:key [x y]
                  :x x :y y
                  :width 0.9 :height 0.9
                  :fill (cond
                          (= [x y] player) "blue"
                          (= [x y] npc) "red"
                          (contains? (set boxes) [x y]) "#333"
                          (get game-map [x y]) "#76C897")}]))]]))

