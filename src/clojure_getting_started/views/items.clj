(ns clojure-getting-started.views.items
  (:require [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [environ.core :refer [env]]))


(defn read-item [item]
    (html5
      [:body
        [:div
          (:content item)]
        [:hr]
        [:div
          [:a {:href "/"} "back"]
          [:br]
          [:a {:href "/"} "edit"]
          [:br]
          [:a {:href "/"} "remove"]
          [:br]]]))

(defn create-item [item & errors]
  (html5
    [:body
      [:div
        (form-to ["POST" "/items"]
          (label :text "Text:")
          (text-field :input)
          [:p errors]
          [:button {:type "submit"} "Send"])]
      [:hr]
      [:div
        [:a {:href "/"} "back"]
        [:br]
        [:a {:href "/"} "edit"]
        [:br]
        [:a {:href "/"} "remove"]
        [:br]]]))

(defn update-item [item]
  (html5
    [:body
      [:div
        (form-to ["PUT" "/items"]
          (label :text "Text:")
          (text-field :input (:content item))
          [:button {:type "submit"} "Send"])]
      [:hr]
      [:div
        [:a {:href "/"} "back"]
        [:br]
        [:a {:href "/"} "edit"]
        [:br]
        [:a {:href "/"} "remove"]
        [:br]]]))
