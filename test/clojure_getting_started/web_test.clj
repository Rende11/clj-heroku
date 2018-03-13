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

(deftest items-post-test-1
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :post "/items") (mock/body {"input" "tost"})))]
    (is (= 302 status))))

(deftest items-post-test-2
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :post "/items") (mock/body {:form-params {:input "tost"}})))]
    (is (= 302 status))))
    
(deftest items-post-test-3
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :post "/items") (mock/body {:input "tost"})))]
   (is (= 302 status))))
        
(deftest items-post-test-4
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :post "/items") (mock/body "tost")))]
    (is (= 302 status))))

(deftest items-get-id-test
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :get "/items/1")))]
    (is (= 200 status))))    

(deftest items-get-edit-test
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :get "/items/1/edit")))]
    (is (= 200 status))))

(deftest items-put-item-test
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :put "/items/1") (mock/body {:input "new item ZZZ"})))]
    (is (= 200 status))))

(deftest items-delete-items-test
  (let [{ status :status headers :headers } (app-routes (-> (mock/request :delete "/items/1")))]
    (is (= 302 status))))

