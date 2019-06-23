(ns balance.http.controller.users-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [balance.handler :refer :all]
            [balance.service.users :as users-service]))

(deftest test-users
  (testing "fetch"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response (app (mock/request :get "/v1/users"))]
        (is (= 200 (:status response)))
        (is (string? (:body response)))
        (let [content (json/read-str (:body response))]
          (is (vector? content))))))

  (testing "save"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (is (= 201 (:status response)))
        (is (nil? (:body response)))
        (is (string? (get-in response [:headers "Location"])))
        (is (string? (get-in response [:headers "X-Resource-Identifier"])))
        (is (= (get-in response [:headers "Location"]) (str "/v1/users/" (get-in response [:headers "X-Resource-Identifier"])))))))

  (testing "save and fetch"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))
      (let [response (app (-> (mock/request :get "/v1/users")))]
        (is (= 200 (:status response)))
        (is (string? (:body response)))
        (let [content (json/read-str (:body response))]
          (is (vector? content))
          (is (= 1 (count content)))
          (is (contains? (get content 0) "id"))
          (is (= "John Doe" (get-in content [0 "name"])))))))

  (testing "find"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (let [response-find (app (-> (mock/request :get (get-in response-save [:headers "Location"]))))]
          (is (= 200 (:status response-find)))
          (is (string? (:body response-find)))
          (let [content (json/read-str (:body response-find))]
            (is (map? content))
            (is (contains? content "id"))
            (is (= "John Doe" (get content "name"))))))))

  (testing "delete"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (let [response-delete (app (-> (mock/request :delete (get-in response-save [:headers "Location"]))))]
          (is (= 204 (:status response-delete)))
          (is (nil? (:body response-delete)))))))

  (testing "save and delete and find"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (let [response-delete (app (-> (mock/request :delete (get-in response-save [:headers "Location"]))))]
          (let [response-find (app (-> (mock/request :get (get-in response-save [:headers "Location"]))))]
            (is (= 404 (:status response-find)))
            (is (string? (:body response-find)))
            (let [content (json/read-str (:body response-find))]
              (is (map? content))
              (is (contains? content "id"))
              (is (= (get content "id") (get-in response-save [:headers "X-Resource-Identifier"])))))))))

  (testing "save multiple"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-a-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (let [response-b-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "João da Silva"})))]
          (let [response-fetch (app (-> (mock/request :get "/v1/users")))]
            (is (= 200 (:status response-fetch)))
            (is (string? (:body response-fetch)))
            (let [content (json/read-str (:body response-fetch))]
              (is (vector? content))
              (is (= 2 (count content)))))))))

  (testing "save with invalid data"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-save (app (-> (mock/request :post "/v1/users") (mock/json-body {:name ""})))]
        (is (= 422 (:status response-save)))
        (is (string? (:body response-save)))
        (let [content (json/read-str (:body response-save))]
          (is (map? content))
          (is (contains? content "errors"))))))

  (testing "delete unknown element"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response-delete (app (-> (mock/request :delete (str "/v1/users/foobar"))))]
        (is (= 404 (:status response-delete)))
        (is (string? (:body response-delete)))
        (let [content (json/read-str (:body response-delete))]
          (is (map? content))
          (is (= "user-unknown-identifier" (get content "type"))))))))
