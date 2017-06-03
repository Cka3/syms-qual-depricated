(ns game.render
  (:require [clojure.string :as str]))

(def images-path "/img")
(defn url [s] (str "url(\"" images-path s "\")"))
(defn px [s] (str s "px"))

(defn substate [key state graph]
  (key (get-in graph (:scene state))))

(def style (partial substate :style))
(def render-type (partial substate :render-type))

(defn process-ptr [subscene-pointer]
  subscene-pointer)

(defn subscene [state graph]
  (-> (substate :subscenes state graph)
      (get-in (process-ptr (:subscene state)))))

(defn actors [state graph]
  (as-> (subscene state graph) <>
    (get <> :characters)
    (map #(get-in graph (concat [:characters] %)) <>)))

(defn dialogue [state graph]
  (get (subscene state graph) :dialogue))

(defn speaker [state graph]
  (get (subscene state graph) :speaker))

(def render-character-xf
  (map
   (fn [{:keys [img alignment]}]
     (let [[x y] (or alignment [0 0])]
       [:div.character
        {:style
         {:background-image img
          :left (px x)
          :top (px y)}}]))))

(defn render-characters [characters]
  (into [:div.characters] render-character-xf characters))

(defn render-textbox [{:keys [window] :as state} graph]
  (let [border-width 3
        padding 16
        margin 5
        padding-sides "20%"
        y (:y window)
        x (:x window)
        height (/ y 5)
        top (* 4 (/ (:y window) 5))
        textbox-height (- height (* 2 (+ border-width padding margin)))]
   [:div.textbox
     {:style {:height (px height)
              :width (px x)
              :top (px top)}}
     [:div.textbox-inner
      {:style
       {:border-width (px border-width)
        :padding-top (px padding)
        :padding-bottom (px padding)
        :margin (px margin)
        :height (px textbox-height)}}
      [:div.textbox-speaker
       (str (str/upper-case (speaker state graph)) ":")]
      [:div.textbox-dialogue (dialogue state graph)]]]))

(defmulti align-characters identity)

(defmethod align-characters :default [arg] arg)

(defmulti render render-type)

(defmethod render :default
  [state-atom graph]
  (let [{:keys [window] :as state} @state-atom]
   [:div.base-scene
     {:style (merge
              {:height (px (:y window))
               :width (px (:x window))}
              (style state graph))}
    (align-characters (render-characters (actors state graph)))
    (render-textbox state graph)]))

(defmulti transition identity)

