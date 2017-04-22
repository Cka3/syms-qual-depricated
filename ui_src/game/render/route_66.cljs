(ns game.render.route-66
  (:require [game.render :as render]
            [game.render.bg :as bg]))

(def data
  {:diner
   {:style {:background-image (:route-66-diner bg/data)}
    :subscenes
    [{:characters [[:roadhog :neutral] [:symmetra :neutral]]
      :speaker "Symmetra"
      :dialogue "Ayyy, what up Roadie?"}
     {:characters [[:roadhog :neutral] [:symmetra :neutral]]
      :speaker "Symmetra"
      :dialogue "Ayyy, what up Roadie?"}
     {:characters [[:roadhog :neutral] [:symmetra :neutral]]
      :speaker "Symmetra"
      :dialogue "Ayyy, what up Roadie?"}
     {:characters [[:roadhog :neutral] [:symmetra :neutral]]
      :speaker "Symmetra"
      :dialogue "Ayyy, what up Roadie?"}]}})

