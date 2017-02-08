(ns ui.core
  (:require [reagent.core :as reagent :refer [atom]]
            [game.core :refer [new-game base-state]]))

(enable-console-print!)

(def state (atom base-state))

(defn app [] (new-game state))

(reagent/render
  [app]
  (js/document.getElementById "app-container"))
