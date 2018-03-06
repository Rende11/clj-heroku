(ns clojure-getting-started.web-test
  (:require [clojure.test :refer :all]
            [clojure-getting-started.web :refer :all]
            [clojure-getting-started.routes.index :refer :all]
            [ring.mock.request :as mock]))

(deftest first-test
  (is true "Tests should be written"))

(deftest index-test
  (let [{ status :status headers :headers } (app-routes (mock/request :get "/"))]
    (is (= 200 status))))

(deftest items-new-test
  (let [{ status :status headers :headers } (app-routes (mock/request :get "/items/new"))]
    (is (= 200 status))))

(deftest items-test
  (let [{ status :status headers :headers } (app-routes (mock/request :get "/items"))]
    (is (= 200 status))))

(deftest items-post-test
  (let [body {:input ""}
        { status :status headers :headers} (app-routes (-> 
                                                          (mock/request :post "/items") 
                                                          (mock/body "body")))]
    (is (= 200888 status))))

    
