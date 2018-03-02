(ns clojure-getting-started.views.index
  (:require [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [environ.core :refer [env]]))


(defn splash [& errors]
  (println errors)
  (html5
    [:body
      [:div
        [:h1 "Header"]]
      [:div
        (form-to ["POST" "/"]
          (label :text "Text:")
          (text-field :input)
          [:button {:type "submit"} "Send"])]
      [:hr]
      [:div
        [:ul 
          (for [result (db/query (env :database-url) ["select id, content from sayings"])]
            [:li
              [:a {:href (str "/" (:id result))} (:content result)]])]]
      [:div
        [:a {:href "/"} "Home"]]]))
