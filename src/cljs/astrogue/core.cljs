(ns astrogue.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [cljs.core.async :refer [chan put! close!]])
    (:require-macros [cljs.core.async.macros :refer [go]]))

(defonce instance (atom 0))

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
                   (swap! game-map assoc [x y] "."))))
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
        [[player] free-cells] (place-items free-cells 1)]
    [game-map boxes player]))

(defn update-player-position! [game-state key-code game-map]
  (let [player (@game-state :player)
        key-map {38 0
                 33 1
                 39 2
                 34 3
                 40 4
                 35 5
                 37 6
                 36 7}
        dirs (js->clj (aget (.-DIRS js/ROT) 8))
        key-dir (get key-map key-code)
        player-diff (get dirs key-dir)
        player-new [(+ (first player) (first player-diff)) (+ (last player) (last player-diff))]]
    (print player-new)
    (if (contains? (set (keys game-map)) (vec player-new))
      (swap! game-state assoc :player player-new))))

(defn draw-map [display game-map player boxes]
  (doall
    (for [[[x y] tile] game-map]
      (.draw display x y
             (cond
               (= [x y] player) "@"
               (contains? (set boxes) [x y]) "*"
               true (get game-map [x y]))))))

(defn input-loop! [display game-map boxes game-state]
  (let [my-instance @instance
        key-chan (make-key-chan)]
    (go
      (loop [key-event nil]
        (when key-event
          (print (.-keyCode key-event))
          (update-player-position! game-state (.-keyCode key-event) game-map))
        (draw-map display game-map (@game-state :player) boxes)
        (if (= my-instance @instance)
          (recur (<! key-chan))
          (print "Exiting loop"))))))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to astrogue"]
   [:div [:a {:href "/about"} "go to about page"]]])

(defn about-page []
  [:div [:h2 "About astrogue"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (swap! instance inc)
  ;(reagent/render [current-page] (.getElementById js/document "app"))
  (let [app-element (.getElementById js/document "app")
        game-state (atom {})
        display (js/ROT.Display.)
        [game-map boxes player] (build-map)]
    (swap! game-state assoc :player player)
    (set! (.-innerHTML app-element) "")
    (.appendChild app-element (.getContainer display))
    (input-loop! display game-map boxes game-state)))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
