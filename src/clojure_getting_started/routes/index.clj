(ns clojure-getting-started.routes.index
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [clojure-getting-started.db.items :as items-db]
            [clojure-getting-started.views.index :as index]
            [clojure-getting-started.views.layout :refer [layout]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defroutes routes
  (GET "/" []
    (layout (index/splash (items-db/get-all-items))))
  (route/resources "/")
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))
