(ns clojure-getting-started.routes.items
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [clojure-getting-started.db.items :as items-db]
            [clojure-getting-started.views.items :as items-view]
            [clojure-getting-started.views.layout :refer [layout]]
            [ring.util.response :refer [redirect]]))
           


(defroutes routes
  (GET "/items/new" []
    (layout (items-view/create-item)))

  (GET "/items" []
    (layout (items-view/show-items (items-db/get-all-items))))

  (POST "/items" [input]
    (if-not (empty? input)
      (do 
        (items-db/record input)
        (redirect "/"))
      (layout (items-view/create-item "empty input"))))
  
  (GET "/items/:id" [id]
    (layout (items-view/read-item (items-db/get-item id))))

  (GET "/items/:id/edit" [id]
    (layout (items-view/update-item (items-db/get-item id))))
  
  (PUT "/items/:id" [id input]
    (if-not (empty? input)
      (do
        (items-db/update-record id input)
        (layout (items-view/read-item (items-db/get-item id))))
      (layout (items-view/update-item (items-db/get-item id) "Cannot be replaced on empty string"))))
  
  (DELETE "/items/:id" [id]
    (items-db/delete-record id)
    (redirect "/")))


