(ns syms-qual.data.scenes.route-66
  (:require [syms-qual.data.bg :as bg]))

(def data
  {:diner
   {:style {:background-image (:route-66-diner bg/data)}
    :subscenes
    [["Symmetra" [[:roadhog :_neutral] [:symmetra :_no]] "Ayyy, what up Roadie?"]
     ["Pharah"  [[:pharah :_cold] [:symmetra :_neutral]] "Ayyy, what up bb it's me ur bird gay."]]
    :transition [:anubis :spawn]}

   :street
   {:style {:background-image (:route-66-street bg/data)}
    :subscenes
    [["Symmetra" [[:roadhog :_neutral] [:symmetra :_neutral]] "Hi, I'm gay."]]}})

