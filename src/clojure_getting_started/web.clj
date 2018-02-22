(ns clojure-getting-started.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [camel-snake-kebab.core :as kebab]
            [clojure.java.jdbc :as db]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]))

(def sample (env :sample "sample-string"))

(defn splash []
  (html5
    (body 
      (div (concat (for [kind ["camel" "snake" "kebab"]])
            (format "<a href=\"/%s?input=%s\">%s %s</a><br />"
              kind sample kind sample))
          ["<hr /><ul>"]
          (for [s (db/query (env :database-url)
                    ["select content from sayings"])]
              (format "<li>%s</li>" (:content s)))
          ["</ul>"]))))

(defn record [input]
  (db/insert! (env :database-url)
    :sayings {:conten input}))



(defroutes app
  (GET "/camel" {{input :input} :params}
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (kebab/->CamelCase input)})
  
  (GET "/snake" {{input :input} :params}
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (kebab/->snake_case input)})
  (GET "/kebab" {{input :input} :params}
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (kebab/->kebab-case input)})
    
  (GET "/" []
       (splash))
  (ANY "*" []
       (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))
