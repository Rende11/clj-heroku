(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes]]
            [compojure.handler :refer [site]]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [environ.core :refer [env]]
            [clojure-getting-started.routes.items :as items]
            [clojure-getting-started.routes.index :as index]))

(defroutes app-routes
    items/routes
    index/routes)

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (wrap-params (site #'app-routes)) {:port port :join? false})))

(defn -main-dev [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (wrap-params (wrap-reload #'app-routes)) {:port port :join? false})))    

