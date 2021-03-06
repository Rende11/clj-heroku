(defproject clojure-getting-started "1.0.0-SNAPSHOT"
  :description "Demo Clojure web app"
  :url "http://clojure-getting-started.herokuapp.com"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.4.0"]
                 [ring "1.4.0"]
                 [environ "1.1.0"]
                 [camel-snake-kebab "0.2.4"]
                 [org.clojure/java.jdbc "0.3.5"]
                 [org.postgresql/postgresql "9.4-1201-jdbc4"]
                 [hiccup "1.0.5"]
                 [jayq "2.5.4"]
                 [ring/ring-mock "0.3.2"]
                 [ring-logger "0.7.7"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :uberjar-name "clojure-getting-started-standalone.jar"
  :profiles {:production {:env {:production true}}
             :dev {:env {:example "ALIVE"}
                   :plugins [[jonase/eastwood "0.2.5"]]}})
             
             
