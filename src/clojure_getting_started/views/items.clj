(ns clojure-getting-started.views.items
  (:require [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [environ.core :refer [env]]))


(defn show-item [item]
  ;; (let [item (db/query (env :database-url) ["select id, content from sayings where id=1"])]
    (html5
      [:body
        [:div
          (:content item)]
        [:hr]
        [:div
          [:a {:href "/"} "back"]
          [:a {:href "/"} "edit"]
          [:a {:href "/"} "remove"]]]))
