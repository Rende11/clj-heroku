(ns clojure-getting-started.views.layout
  (:require [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]))

(defn header []
  [:head
    [:title "Clojure CRUD"]
    [:meta {:charset "utf-8"}]
    [:link {:rel "stylesheet" :href "/css/site.css"}]])

(defn layout [content]
  (html5
    (header)
    [:body {:class "body-container"}
      [:hr]
      content]))
      
