(ns carmen.miranda
  (:require [carmen.impl.render :as render :refer [render]]))

(def -state (atom {}))

(defn resize-state [settings]
  (let [xscrn window.innerWidth
        yscrn window.innerHeight]
    {:x xscrn :y yscrn}))

(defn resize-event [state-atom]
  (fn []
    (swap!
     state-atom
     (fn [game]
       (assoc game :window (resize-state (:settings game)))))))

(defn clear-listeners! []
  (doseq [[k v] @-state]
    (cond
      (= k :interval) (js/window.clearInterval v)
      :default (js/window.removeEventListener k v))))

(defn render-game-inner [state-atom graph options]
  [render @state-atom graph options])

(defn register-listener! [state-atom body event-name event]
  (js/window.addEventListener event-name event)
  (swap! -state #(assoc % event-name event)))

(defn samba [state-atom graph options]
  (fn render-game []
    [render-game-inner state-atom graph options]))

(defn transition-event [state-atom]
  (fn []
    (swap!
     state-atom
     (fn [state]
       (update-in state [:scene 2] inc)))))
