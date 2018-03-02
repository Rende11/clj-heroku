(ns clojure-getting-started.views.index
  (:require [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [environ.core :refer [env]]))


(defn splash [items & errors]
  (println errors)
  (html5
    [:body
      [:div
        [:h1 "Header"]]
      [:div
        (form-to ["POST" "/items"]
          (label :text "Text:")
          (text-field :input)
          [:button {:type "submit"} "Send"])]
      [:hr]
      [:div
        [:ul 
          (for [item items]
            [:li
              [:a {:href (str "/items/" (:id item))} (:content item)]])]]
      [:div
        [:a {:href "/"} "Home"]]]))
