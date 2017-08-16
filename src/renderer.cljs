(ns astrogue.renderer)

(defn rectangle-in-rectangle [r1 r2]
  (let [scale (js/Math.min (/ (first r2) (first r1)) (/ (last r2) (last r1)))]
    (vec (map #(int (* % scale)) r1))))

(defn resize-screen! [c dimensions]
  (reset! c
          (rectangle-in-rectangle dimensions [(.-innerWidth js/window) (.-innerHeight js/window)])))

(defn send-chat-message [db send-fn]
  (let [m (@db :message)]
    (when m
      (send-fn m)
      (swap! db dissoc :message))))

(defn hex [a]
  (apply str (map #(.slice (str "0" (.toString (bit-and 0xff %) 16)) -2) (js/Array.from a))))

(defn component-chat [db config message-cb]
  [:div#chat
   [:div#messages
    (for [m (@db :messages)]
      [:div {:key (hex (get m "uid"))} [:span.id (hex (subvec (get m "key") 0 4))] [:span.message (get m "message")]] )]
   [:div.ui
    [:button {:on-click #(send-chat-message db message-cb)} "send"]   
    [:input {:value (@db :message)
             :placeholder "chat..."
             :on-key-up #(if (= (-> % .-keyCode) 13) (send-chat-message db message-cb))
             :on-change #(swap! db assoc :message (-> % .-target .-value))}]]])

(defn component-user-config [config]
  [:div#config
   [:input {:value (@config :name)
            :placeholder "nickname..."
            :on-change #(swap! config assoc :name (-> % .-target .-value))}]])

(defn component-renderer [dimensions db game-map boxes clock config message-cb]
  (let [player (@db :player)
        npc (@db :npc)
        ratio (/ 2 3)
        w (first dimensions)
        h (last dimensions)]
    [:div
     [component-user-config config]
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
                (if overlay [:span.overlay overlay]))])]))]
     [component-chat db config message-cb]]))

