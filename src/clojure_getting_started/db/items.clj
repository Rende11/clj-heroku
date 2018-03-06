(ns clojure-getting-started.db.items
  (:require [clojure.java.jdbc :as db]
            [environ.core :refer [env]]))

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
