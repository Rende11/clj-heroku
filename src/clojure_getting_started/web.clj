(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [redirect]]
            [environ.core :refer [env]]
            [camel-snake-kebab.core :as kebab]
            [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [hiccup.form :refer :all]
            [clojure-getting-started.views.index :refer [splash]]
            [clojure-getting-started.views.items :refer [read-item create-item update-item show-items]]))

(def sample (env :sample))
(def database-url (env :database-url))

(defn record [input]
  (db/insert! database-url
    :sayings {:content input}))

(defn get-all-items []
  (db/query database-url ["select * from sayings order by id asc"]))

(defn get-item [id]
  (first (db/query database-url ["select * from sayings where id = ?" (Integer. id)])))
  
(defn update-record [id content]
  (db/update! database-url :sayings {:content content} ["id = ?" (Integer. id)]))

(defn delete-record [id]
  (db/delete! database-url :sayings ["id = ?" (Integer. id)]))


(defroutes app
  (GET "/" []
    (splash (get-all-items)))
  
  (GET "/items/new" []
    (create-item))
  
  (GET "/items" []
    (show-items (get-all-items)))

  (POST "/items" [input]
    (if-not (empty? input)
      (do 
        (record input)
        (redirect "/"))
      (create-item "empty input")))
  
  (GET "/items/:id" [id]
    (read-item (get-item id)))

  (GET "/items/:id/edit" [id]
    (update-item (get-item id)))
  
  (PUT "/items/:id" [id input]
    (if-not (empty? input)
      (do
        (update-record id input)
        (read-item (get-item id)))
      (update-item (get-item id) "Cannot be replaced on empty string")))
  
  (DELETE "/items/:id" [id]
    (delete-record id)
    (redirect "/"))

  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

(defn -main-dev [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (wrap-params (wrap-reload #'app)) {:port port :join? false})))    

