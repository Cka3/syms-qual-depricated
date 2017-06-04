(ns syms-qual.data.scenes.anubis
  (:require [syms-qual.data.bg :as bg]))

(def data
  {:spawn
   {:style {:background-image (:anubis-spawn bg/data)}
    :subscenes
    ["Symmetra" [[:roadhog :neutral] [:symmetra :neutral]] "Awwww shit boy."]}})
