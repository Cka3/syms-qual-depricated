(ns syms-qual.data.bg
  (:require [carmen.data :as data]))

(def structure
  ["route_66_diner"
   "route_66_street"

   "anubis_cafe"
   "anubis_flashback"
   "anubis_spawn"

   "volskaya_spawn"])

(def options
  {:path "img/Backgrounds/"
   :ext  "png"})

(def data
  (data/reify-bgs structure options))
