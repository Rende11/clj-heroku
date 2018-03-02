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
            [hiccup.form :refer :all]))

(def sample (env :sample))

(defn splash []
  (html5
    [:body
      [:div
        [:p
          [:h1 "Header"]]]
      [:div
        (form-to ["POST" "/"]
          (label :text "Text:")
          (text-field :input)
          [:button {:type "submit"} "Send"])]
      [:div
        [:ul 
          (for [kind ["camel" "snake" "kebab"]]
            [:li
              [:a {:href (str "/" kind "?input=" sample)} kind]])]]
      [:hr]
      [:div
        [:ul 
          (for [result (db/query (env :database-url) ["select content from sayings"])]
            [:li
              (:content result)])]]
      [:div
        [:a {:href "/"} "Homes"]]]))

(defn record [input]
  (db/insert! (env :database-url)
    :sayings {:content input}))


(defroutes app

  (GET "/kek/:name" [name]
    (str name))

  (GET "/add/:input" [input]
    (record input)
    (splash))

  (GET "/camel" {{input :input} :params}
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (kebab/->CamelCase input)})
  
  (GET "/test" [request]
   {:status 200
    :headers {"Content-Type" "text/plain"}
    :body "(kebab/->CamelCase input)"})
  
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
  
  (POST "/" [input]
   (str input))
  
  (ANY "*" []
    (route/not-found (slurp (io/resource "404.html")))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

(defn -main-dev [& [port]]
  (let [port (Integer. (or port (env :port) 5005))]
    (jetty/run-jetty (wrap-params (wrap-reload #'app)) {:port port :join? false})))    

