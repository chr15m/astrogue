(ns astrogue.renderer)

(defn rectangle-in-rectangle [r1 r2]
  (let [scale (js/Math.min (/ (first r2) (first r1)) (/ (last r2) (last r1)))]
    (vec (map #(int (* % scale)) r1))))

(defn resize-screen! [c dimensions]
  (reset! c
          (rectangle-in-rectangle dimensions [(.-innerWidth js/window) (.-innerHeight js/window)])))

(defn component-renderer [dimensions db game-map boxes clock]
  (let [player (@db :player)
        npc (@db :npc)
        ratio (/ 2 3)
        w (first dimensions)
        h (last dimensions)]
    [:div
     [:pre#canvas
      (doall
        (for [y (range h)]
          [:p {:key y}
           (for [x (range w)]
             [:span.floor {:key [x y]}
               (cond
                 ; grass
                 (get game-map [x y])
                 "▒"

                 ; empty map
                 (nil? (get game-map [x y]))
                 " ")
              (let [overlay (cond
                              ; player
                              (= [x y] player)
                              "●"

                              ; entities
                              (= [x y] npc) "▼"
                              (contains? (set boxes) [x y]) "■")]
                (if overlay [:span.overlay overlay]))])]))]]))

