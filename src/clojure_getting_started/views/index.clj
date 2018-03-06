(ns clojure-getting-started.views.index
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))



(defn splash [items & errors]
  (html5
    [:body
      [:hr]
      [:div
        [:a {:href "/items/new"} "Add"]]
      [:div
        [:ul 
          (for [item items]
            [:li
              [:a {:href (str "/items/" (:id item))} (:content item)]])]]
      [:div
        [:a {:href "/items"} "All items "]]]))
