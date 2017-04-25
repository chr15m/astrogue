(ns astrogue.renderer)

(def shape-player (str "m 47.600815,17.9372 c -0.101451,3.253664 -1.138592,6.561822 -3.11362,9.163908 -1.279685,1.853662 -3.276461,2.846127 -5.374747,3.477753 -2.700786,0.01979 -2.498849,2.445447 -0.497063,3.470764 1.707458,1.574715 3.484555,3.116172 4.264296,5.322094 1.588692,0.670217 3.85307,0.439932 3.968784,2.906434 0.855664,2.224823 -0.438512,4.805498 -3.008203,4.690827 -2.048127,0.83762 -5.697947,-0.534555 -3.784126,-3.107062 1.007237,-2.047551 -0.09776,-5.44072 -2.207936,-6.128484 -1.371878,1.648788 -0.131695,4.627584 -2.021309,6.138297 -1.935002,1.689367 -4.701629,1.551055 -7.104386,2.081787 -3.111878,0.538058 -6.332204,0.329187 -9.421971,-0.234162 -2.22038,-0.621567 -3.898906,-2.277037 -4.40266,-4.556296 -1.229254,-1.458318 0.264575,-5.157694 -1.800045,-5.237647 -1.400509,1.353586 -4.9444857,3.274123 -3.7017365,5.365891 1.9801585,1.282195 0.4544427,3.883022 -1.4917357,4.076555 C 5.8721687,45.985931 2.7740382,45.163323 3.1144649,42.505554 3.2377274,40.154238 6.4063782,40.421306 7.511649,38.669516 9.2707156,37.22946 10.070145,35.022894 12.029343,33.7719 13.36526,32.972203 14.708891,30.922876 12.313314,30.523618 10.268027,30.109676 8.6952832,28.760833 7.5903581,27.028988 5.8232508,24.346798 4.1094493,21.369329 4.3415885,18.034777 4.1634027,14.952729 4.7752884,11.717559 6.7186227,9.2534502 8.2636834,7.1182493 9.9772633,4.983188 12.164685,3.4746542 14.08339,2.180847 16.206562,1.1376638 18.564448,1.0936975 c 4.605871,-0.27984518 9.263207,-0.15289 13.765699,0.9421245 2.948122,0.5100634 5.998363,1.0466985 8.441211,2.8929791 2.683537,1.6286989 5.077293,3.8942372 6.205693,6.8836709 1.175109,1.861379 0.648396,4.066184 0.623764,6.124728 z M 44.105177,14.013371 C 43.348639,11.490444 41.262229,9.6909516 39.505615,7.8263976 "
                       "37.685837,6.0864351 35.25807,5.0875141 32.867241,4.3876329 27.614352,3.5810626 22.154036,2.9604578 16.910637,4.1380584 13.614559,5.3787133 11.235375,8.0866136 8.809322,10.515863 c -2.0672711,2.89524 -1.8410826,6.797411 -1.0527967,10.100288 0.8465129,2.401042 1.9724151,4.95842 4.0481337,6.543202 3.565168,1.879969 7.676001,1.894265 11.555954,2.5905 4.931255,0.431629 9.865778,-0.344222 14.719429,-1.126748 2.593015,-0.400226 4.181973,-2.318224 5.116866,-4.624421 0.978966,-1.899253 1.457725,-3.994831 1.294138,-6.132303 -0.08662,-1.269393 0.26052,-2.686288 -0.385869,-3.85301 z m -7.339788,7.994867 c -0.597369,2.447012 -2.423195,5.166531 -5.281491,3.637545 -2.054505,-1.044931 -1.401924,-3.900849 -1.68668,-5.81626 -0.160449,-2.160977 1.030458,-5.115095 3.64184,-4.81238 2.339732,-0.09235 3.802619,2.279869 3.755648,4.412802 0.03554,0.87567 -0.09004,1.766914 -0.429317,2.578293 z M 20.233457,18.669986 c -0.49354,2.347536 -1.046464,5.357299 -3.344748,6.521426 -2.021292,0.255355 -4.183142,-0.737648 -4.973662,-2.686787 -1.563768,-3.857299 -1.482298,-8.597096 0.936494,-12.089433 1.332814,-1.6176234 4.542004,-2.6929956 6.170122,-1.1113953 1.14528,2.7273103 1.324936,5.8157273 1.289432,8.7537273 l -0.03247,0.33257 z m 13.84477,17.920335 c -0.84589,-2.320982) -1.918269,-5.408544 -5.038398,-4.416474 -3.217502,0.300888 -6.442401,-0.116608 -9.656259,-0.06629 -2.322603,0.575585 -2.753195,3.711767 -2.828868,5.781195 -0.129929,2.68328 1.86704,4.943397 4.302108,5.789308 1.741996,0.208697 3.577824,0.0045 5.350132,0.05222 2.273843,-0.13775 4.680617,0.01865 6.8156,-0.887084 1.474165,-1.667451 1.303892,-4.18119 1.055685,-6.252875 z"))

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
                   :x (- x (first player) 0.45) :y (* (- y (last player) 0.45) ratio)
                   :width 0.9 :height (* 0.9 ratio)
                   :rx 0.09 :ry 0.06
                   :fill (cond
                           ;(= [x y] player) "blue"
                           (= [x y] npc) "red"
                           (contains? (set boxes) [x y]) "#333"
                           (get game-map [x y]) "#76C897")}]))
       (doall
         (for [[[x y] tile] game-map]
           (cond (= [x y] npc) nil
                 (contains? (set boxes) [x y]) nil)))
       [:svg#player
        {:width 1 :height 1 :viewBox "0 0 48 48" :x -0.5 :y -1.25}
        [:path {:transform "translate(2,0)" :d "m 44.571428,17.285715 a 20.857143,15.285714 0 1 1 -41.7142865,0 20.857143,15.285714 0 1 1 41.7142865,0 z" :fill "white"}]
        [:path {:transform "translate(2.5714286,0.8571429)" :d "m 33.428573,37.285713 a 10.142858,8.1428566 0 1 1 -20.285715,0 10.142858,8.1428566 0 1 1 20.285715,0 z" :fill "white"}]
        [:path {:d shape-player}]]]]]))

