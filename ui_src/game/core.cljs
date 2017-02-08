(ns game.core
  (:require [game.render :refer [render]]
            [game.render.menu]))

(def base-state
  {:scene :menu})

(defn new-game [state-atom]
  (fn render-game []
    [render @state-atom]))
