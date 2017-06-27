(ns carmen.impl.render
  (:require [clojure.string :as str]))

(defn px [s] (str s "px"))

(defn scene-data [state graph]
  (let [[major minor & _] (:scene state)]
    (-> (:scenes graph)
        (major)
        (minor))))

(defn style [state graph]
  (:style (scene-data state graph)))

(defn render-type [state graph options]
  (:render-type (scene-data state graph)))

(defn subscene-ptr [state]
  (let [[major minor & ptr] (:scene state)]
    ptr))

(defn subscene [state graph]
  (-> (scene-data state graph)
      (:subscenes)
      (get-in (subscene-ptr state))))

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

(def default-options
  {:border-width 3
   :padding 16
   :margin 5
   :ratio 0.2})

(defn render-dialogue-textbox [{:keys [window] :as state} graph options]
  (let [{:keys [border-width padding margin ratio]} (merge default-options options)
        y (:y window)
        x (:x window)
        height (* y ratio)
        top (* (- 1 ratio) (:y window))
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

(defn render-textbox [{:keys [window] :as state} graph options]
  (let [{:keys [border-width padding margin ratio]} (merge default-options options)
        y (:y window)
        x (:x window)
        height (* y ratio)
        top (* (- 1 ratio) (:y window))
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
      [:div.textbox-dialogue (subscene state graph)]]]))

(defmulti align-characters identity)

(defmethod align-characters :default [arg] arg)

(defmulti render render-type)

(defmethod render :textbox
  [{:keys [window] :as state} graph options]
  [:div.base-scene
   {:style (merge
            {:height (px (:y window))
             :width (px (:x window))}
            (style state graph))}
   (render-textbox state graph options)])

(defmethod render :dialogue
  [{:keys [window] :as state} graph options]
  [:div.base-scene
   {:style (merge
            {:height (px (:y window))
             :width (px (:x window))}
            (style state graph))}
   (align-characters (render-characters (actors state graph)))
   (render-textbox state graph options)])

