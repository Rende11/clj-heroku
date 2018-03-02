(ns clojure-getting-started.views.record
  (:require [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [environ.core :refer [env]]))


(defn show-item [id]
  (let [item (db/query (env :database-url) ["select id, content from sayings where id=1"])]
    (html5
      [:body
        [:div
          (:content item)]
        [:hr]
        [:div
          [:a {:href "/"} "Home"]]])))
