(ns astrogue.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

(defn generate-map []
  (let [d (js/ROT.Map.Digger.)
        game-map (atom {})]
    (.create d (fn [x y tile]
                 (if (= tile 0)
                   (swap! game-map assoc [x y] "."))))
    @game-map))

(defn place-boxes [game-map]
  (loop [i 10
         box-positions []
         free-cells (keys game-map)]
    (if (> i 0)
      (let [chosen (js/Math.floor (* (-> js/ROT .-RNG (.getUniform)) (count free-cells)))]
        (recur
          (dec i)
          (conj box-positions (nth free-cells chosen))
          (remove #{chosen} free-cells)))
      box-positions)))

(defn draw-map [display game-map boxes]
  (doall
    (for [[[x y] tile] game-map]
      (.draw display x y
             (if (contains? (set boxes) [x y])
               "*"
               (get game-map [x y]))))))

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
  ;(reagent/render [current-page] (.getElementById js/document "app"))
  (let [app-element (.getElementById js/document "app")
        display (js/ROT.Display.)
        game-map (generate-map)
        boxes (place-boxes game-map)]
    (set! (.-innerHTML app-element) "")
    (.appendChild app-element (.getContainer display))
    (draw-map display game-map boxes)))

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
