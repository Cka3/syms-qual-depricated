(ns syms-qual.data.characters
  (:require [carmen.data :as data]))

(def structure
  {"Symmetra"
   {"_neutral" {}}

   "Roadhog"
   {"_neutral" {}}

   "Pharah"
   {"_geez" {:alignment [0 0]}
    "_cold" {:alignment [0 0]}}})

(def options
  {:path "img/Characters/"
   :ext  "png"})

(def data (data/reify-characters structure options))
