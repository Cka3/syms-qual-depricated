(ns game.render.character
  (:require [game.render :refer [url]]))

(def data
  {:symmetra
   {:neutral {:img (url "/Characters/Symmetra/neutral.png")}}

   :roadhog
   {:neutral {:img (url "/Characters/Roadhog/neutral.png")
              :alignment [0 0]}}})
 
