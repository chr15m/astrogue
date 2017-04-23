(ns astrogue.engine
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [cljs.core.async :refer [chan put! close!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce instance (atom 0))

(def keys-directional {38 0
                       33 1
                       39 2
                       34 3
                       40 4
                       35 5
                       37 6
                       36 7})
(def keys-action #{32 13})

(defn <<< [f & args]
  (let [c (chan)] ()
    (apply f (concat args [(fn
                             ([] (close! c))
                             ([& x] (if (= (count x) 1) (put! c (first x)) (put! c x))))]))
    c))

(defn make-key-chan []
  (<<< #(.addEventListener js/window "keydown" %)))

(defn generate-map []
  (let [d (js/ROT.Map.Digger.)
        game-map (atom {})]
    (.create d (fn [x y tile]
                 (if (= tile 0)
                   (swap! game-map assoc [x y] true))))
    @game-map))

(defn place-items [free-cells c]
  (loop [i c
         item-positions []
         free-cells-reduced free-cells]
    (if (> i 0)
      (let [chosen (js/Math.floor (* (-> js/ROT .-RNG (.getUniform)) (count free-cells)))]
        (recur
          (dec i)
          (conj item-positions (nth free-cells chosen))
          (remove #{chosen} free-cells)))
      [item-positions free-cells-reduced])))
 
(defn build-map []
  (let [game-map (generate-map)
        free-cells (keys game-map)
        [boxes free-cells] (place-items free-cells 10)
        [[player] free-cells] (place-items free-cells 1)
        [[npc] free-cells] (place-items free-cells 1)]
    [game-map boxes player npc]))

(defn update-player-position! [game-state key-code game-map]
  (let [player (@game-state :player)
        dirs (js->clj (aget (.-DIRS js/ROT) 8))
        key-dir (get keys-directional key-code)
        player-diff (get dirs key-dir)
        player-new [(+ (first player) (first player-diff)) (+ (last player) (last player-diff))]]
    (if (contains? (set (keys game-map)) (vec player-new))
      (swap! game-state assoc :player player-new))))

(defn update-npc-position! [game-state game-map]
  (let [player (@game-state :player)
        npc (@game-state :npc)
        map-tiles (set (keys game-map))
        a* (js/ROT.Path.AStar. (first player) (last player)
                               (fn [x y] (contains? map-tiles [x y]))
                               {:topology 4})
        path (atom [])]
    (.compute a* (first npc) (last npc) (fn [x y] (swap! path conj [x y])))
    (swap! path subvec 1)
    (if (= (count @path) 1)
      "caught"
      (do
        (swap! game-state assoc :npc (first @path))
        nil))))

(defn draw-map [display game-map player npc boxes]
  (doall
    (for [[[x y] tile] game-map]
      (.draw display x y
             (cond
               (= [x y] player) "●"
               (= [x y] npc) "▲"
               (contains? (set boxes) [x y]) "▢"
               (get game-map [x y]) "·")))))

(defn input-loop! [display game-map boxes game-state]
  (let [my-instance @instance
        key-chan (make-key-chan)]
    (go
      (loop [key-event nil
             win-state nil]
        (when key-event
          (let [k (.-keyCode key-event)
                player (@game-state :player)]
            (print "key" k)
            (update-player-position! game-state k game-map)
            (when (and (contains? keys-action k) (contains? (set boxes) player))
              (if (= player (first boxes))
                (recur nil "won")
                (js/alert "Empty box.")))
            (if (contains? (set (concat keys-action (keys keys-directional))) k)
              (let [npc-win-state (update-npc-position! game-state game-map)]
                (if npc-win-state
                  (recur nil npc-win-state))))))
        (draw-map display game-map (@game-state :player) (@game-state :npc) boxes)
        (if (and (= my-instance @instance) (nil? win-state))
          (recur (<! key-chan) nil)
          (do
            (print "Exiting loop.")
            win-state))))))

(defn wait-for-win! [display game-map boxes game-state]
  (swap! instance inc)
  (let [my-instance @instance]
    (go
      (loop [win-state nil]
        (if win-state
          (cond
            (= win-state "caught") (js/alert "You were caught.")
            (= win-state "won") (js/alert "You found the prize. You win."))
          (if (= my-instance @instance)
            (recur (<! (input-loop! display game-map boxes game-state)))))))))

