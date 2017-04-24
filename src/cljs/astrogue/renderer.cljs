(ns astrogue.renderer)

(defn rectangle-in-rectangle [r1 r2]
  (let [scale (js/Math.min (/ (first r2) (first r1)) (/ (last r2) (last r1)))]
    (vec (map #(int (* % scale)) r1))))

(defn resize-screen! [c dimensions]
  (reset! c
          (rectangle-in-rectangle dimensions [(.-innerWidth js/window) (.-innerHeight js/window)])))

(defn component-renderer [dimensions db game-map boxes]
  (let [player (@db :player)
        npc (@db :npc)
        ratio (/ 2 3)
        w (first dimensions)
        h (last dimensions)]
    [:div
     [:svg#canvas {:width (.-innerWidth js/window)
                   :height (.-innerHeight js/window)
                   :viewBox (str (* (/ w 2) -1) " " (* (/ h 2) -1) " " w " " h)}
      [:g {:transform "scale(4)"}
       (doall
         (for [[[x y] tile] game-map]
           [:rect {:key [x y]
                   :x (- x (first player) 0.5) :y (* (- y (last player) 0.5) ratio)
                   :width 0.9 :height (* 0.9 ratio)
                   :fill (cond
                           (= [x y] player) "blue"
                           (= [x y] npc) "red"
                           (contains? (set boxes) [x y]) "#333"
                           (get game-map [x y]) "#76C897")}]))]]]))

