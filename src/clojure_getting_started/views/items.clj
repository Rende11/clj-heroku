(ns clojure-getting-started.views.items
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn footer-menu [id]
  [:div
    [:hr]
    [:a {:href "/"} "back"]
    [:br]
    [:a {:href (str "/items/" id "/edit")} "edit"]
    [:br]
    (form-to [:delete (str "/items/" id)]
      [:button {:type "submit"} "Delete"])])

(defn read-item [item]
  [:div
    (:content item)
    (footer-menu (:id item))])
  

(defn show-items [items]
  [:body {:class "body-container"}
    [:div
      [:table
        [:th "id"]
        [:th "content"]
        (for [item items]
          [:tr
            [:td (:id item)]
            [:td (:content item)]])]]
    [:div
      [:a {:href "/"} "Home"]]])


(defn create-item [& errors]

  [:body {:class "body-container"}
    [:div
      (form-to ["POST" "/items"]
        (label :text "Text: ")
        (text-field :input)
        [:p errors]
        [:button {:type "submit"} "Send"])]
    [:hr]
    [:div
      [:a {:href "/"} "back"]
      [:br]]])

(defn update-item [item & errors]
  [:body {:class "body-container"}
    [:div
      (form-to [:put (str "/items/" (:id item))]
        (label :text "Text:")
        (text-field :input (:content item))
        [:p errors]
        [:button {:type "submit"} "Send"])
      (footer-menu (:id item))]])
