(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [environ.core :refer [env]]
            [camel-snake-kebab.core :as kebab]
            [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [clojure-getting-started.views.index :refer [splash]]
            [clojure-getting-started.views.items :refer [show-item]]))

(def sample (env :sample))
(def database-url (env :database-url))

(defn record [input]
  (db/insert! database-url
    :sayings {:content input}))

(defn get-all-items []
  (db/query database-url ["select * from sayings"]))

(defn get-item [id]
  (first (db/query database-url ["select * from sayings where id = ?" (Integer. id)])))
  

(defroutes app

  (GET "/items/:id" [id]
    (show-item (get-item id)))
    
  (GET "/" []
       (splash (get-all-items)))
  
  (POST "/items" [input]
    (if-not (empty? input)
      (do 
        (record input)
        (splash "empty string")))
    (splash))
  
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

(defn -main-dev [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (wrap-params (wrap-reload #'app)) {:port port :join? false})))    

