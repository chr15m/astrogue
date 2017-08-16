(ns astrogue.core
  (:require [reagent.core :as reagent]
            [clojure.core.async :refer [<!]]
            [alandipert.storage-atom :refer [local-storage]]
            [astrogue.engine :refer [build-map make-game-state wait-for-win!]]
            [astrogue.renderer :refer [component-renderer resize-screen!]]
            [astrogue.multiplayer :as multiplayer]
            [astrogue.utils :refer [generate-dungeon-name dungeon-name-to-seed]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

; get profile from localstorage or create
(defonce config (local-storage (reagent/atom (multiplayer/make-profile)) :config))
(defonce wt (js/WebTorrent.))
(defonce room (atom nil))

(defn send-chat-message [config game-state room message]
  (multiplayer/post @room {:type "chat"
                           :key (@game-state :public-key)
                           :name (@config :name)
                           :message message}
                    (@config :secret-key)))

(defonce instance (atom 0))
(defn listen-for-messages [room game-state]
  (swap! instance inc)
  (print "listen-for-messages entering loop:" @instance)
  (let [my-instance @instance]
    (go-loop []
             (let [[action message] (<! (@room :chan))
                   message-clj (js->clj message)]
               (println "Got a value in this loop:" action message-clj)
               (when (and (= action "message") (= (get message-clj "type") "chat"))
                 (swap! game-state update-in [:messages] conj message-clj))
               (if (and action (= my-instance @instance))
                 (recur)
                 (print "listen-for-messages exiting loop:" my-instance))))))

(defn mount-root []
  (let [url-hash (.. js/document -location -hash)
        url-hash (if (= url-hash "") "astrogue" (.replace url-hash "#" ""))
        dungeon-seed (dungeon-name-to-seed url-hash)
        dungeon-hash (str "astrogue-room:" dungeon-seed)
        rng (js/ROT.RNG.setSeed dungeon-seed)
        dimensions [80 25]
        
        app-element (.getElementById js/document "app")
        game-state (reagent/atom (make-game-state))
        
        clock (reagent/atom 0)
        [game-map boxes player npc] (build-map)
        handle-resize-event! (partial resize-screen! (reagent/cursor game-state [:window]) dimensions)]
    (print "dungeon-seeds" url-hash dungeon-seed)
    (swap! game-state assoc :public-key (multiplayer/pk-from-secret (@config :secret-key)))
    (swap! game-state assoc :player player)
    (swap! game-state assoc :npc npc)
    (swap! room multiplayer/set-room wt dungeon-hash)
    (listen-for-messages room game-state)
    (handle-resize-event!)
    (.addEventListener js/window "resize" handle-resize-event!)
    ;(js/setInterval (fn [] (reset! clock (.getTime (js/Date.)))) 100)
    (reagent/render [component-renderer dimensions game-state game-map boxes clock config (partial send-chat-message config game-state room)] (.getElementById js/document "app"))
    ;(set! (.-innerHTML app-element) "")
    ;(.appendChild app-element (.getContainer display))
    (wait-for-win! game-map boxes game-state)))

(defn init! []
  (mount-root))
