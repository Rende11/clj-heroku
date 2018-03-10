(ns clojure-getting-started.views.items
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))


(defn read-item [item]
    (html5
      [:body
        [:div
          (:content item)]
        [:hr]
        [:div
          [:a {:href "/"} "back"]
          [:br]
          [:a {:href (str "/items/" (:id item) "/edit")} "edit"]
          [:br]
          (form-to [:delete (str "/items/" (:id item))]
            [:button {:type "submit"} "Delete"])
          [:br]]]))

(defn show-items [items]
  (html5
    [:body
      [:div
        [:table
          [:th "id"]
          [:th "content"]
          (for [item items]
            [:tr
              [:td (:id item)]
              [:td (:content item)]])]]
      [:div
        [:a {:href "/"} "Home"]]]))


(defn create-item [& errors]
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
        [:br]]]))

(defn update-item [item & errors]
  (html5
    [:body
      [:div
        (form-to [:put (str "/items/" (:id item))]
          (label :text "Text:")
          (text-field :input (:content item))
          [:p errors]
          [:button {:type "submit"} "Send"])]
      [:hr]
      [:div
        [:a {:href "/"} "back"]
        [:br]
        [:a {:href (str "/items/" (:id item) "/edit")} "edit"]
        [:br]
        (form-to [:delete (str "/items/" (:id item))]
          [:button {:type "submit"} "Delete"])
        [:br]]]))
