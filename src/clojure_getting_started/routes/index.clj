(ns clojure-getting-started.routes.index
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [clojure-getting-started.db.items :as items-db]
            [clojure-getting-started.views.index :as index]
            [compojure.route :as route]
            [clojure.java.io :as io]))

(defroutes routes
  (GET "/" []
    (index/splash (items-db/get-all-items)))
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))
