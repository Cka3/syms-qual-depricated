(ns ui.core
  (:require [reagent.core :as reagent :refer [atom]]
            [game.core :refer [new-game base-state register-state!]]))

(enable-console-print!)

(def state (atom base-state))

(defn app [] (new-game state))

(def root (. js/document (getElementById "app-container")))

(register-state! state root)

(reagent/render [app] root)
