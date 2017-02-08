(ns game.render.menu
  (:require [game.render :refer [render]]))

(defmethod render :menu
  [state]
  [:div "Hello world!"])

