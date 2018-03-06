(ns clojure-getting-started.routes.items
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [clojure-getting-started.db.items :as items-db]
            [clojure-getting-started.views.items :as items-view]
            [ring.util.response :refer [redirect]]))
           


(defroutes routes
  (GET "/items/new" []
    (items-view/create-item))
  
  (GET "/items" []
    (items-view/show-items (items-db/get-all-items)))

  (POST "/items" [input]
    (if-not (empty? input)
      (do 
        (items-db/record input)
        (redirect "/"))
      (items-view/create-item "empty input")))
  
  (GET "/items/:id" [id]
    (items-view/read-item (items-db/get-item id)))

  (GET "/items/:id/edit" [id]
    (items-view/update-item (items-db/get-item id)))
  
  (PUT "/items/:id" [id input]
    (if-not (empty? input)
      (do
        (items-db/update-record id input)
        (items-view/read-item (items-db/get-item id)))
      (items-view/update-item (items-db/get-item id) "Cannot be replaced on empty string")))
  
  (DELETE "/items/:id" [id]
    (items-db/delete-record id)
    (redirect "/")))


