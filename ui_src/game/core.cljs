(ns game.core
  (:require [game.render :as render :refer [render]]
            [game.render.character :as character]
            [game.render.menu :as menu]
            [game.render.route-66 :as route-66]))

(def graph
  {:characters character/data
   :menu menu/data
   :route-66 route-66/data})

(def base-state
  {:scene [:route-66 :intro]})

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

(defn clear-state! []
  (doseq [[k v] @-state]
    (cond
      (= k :interval) (js/window.clearInterval v)
      :default (js/window.removeEventListener k v))))

(defn register-state! [state-atom body]
  (clear-state!)
  (let [resize-fn (resize-event state-atom)]
    (js/window.addEventListener "resize" resize-fn)
    (swap! -state #(assoc % "resize" resize-fn))
    (resize-fn)))

(defn new-game [state-atom]
  (fn render-game []
    [render state-atom graph]))
