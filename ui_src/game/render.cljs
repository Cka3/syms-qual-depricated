(ns game.render)

(def images-path "/img")
(defn url [s] (str "url(\"" images-path s "\")"))
(defn px [s] (str s "px"))

(defn substate [key state graph]
  (key (get-in graph (:scene state))))

(def actors (partial substate :characters))
(def style (partial substate :style))
(def render-type (partial substate :render-type))

(def render-character-xf
  (map
   (fn [{:keys [img]}]
     [:div
      {:style {:background-image img}}])))

(defn render-characters [characters]
  (into [:div.characters] render-character-xf characters))

(defmulti render render-type)

(defmethod render :default
  [state-atom graph]
  (let [{:keys [window] :as state} @state-atom]
   [:div.base-scene
     {:style (merge
              {:height (px (:y window))
               :width (px (:x window))
               :background-size "cover"}
              (style state graph))}
     (render-characters (actors state graph))]))

(defmulti transition identity)

