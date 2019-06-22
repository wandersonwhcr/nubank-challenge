(ns balance.http.controller.users-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [balance.handler :refer :all]
            [balance.service.users :as users-service]))

(deftest test-users
  (testing "users controller at fetch action"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response (app (mock/request :get "/v1/users"))]
        (is (= 200 (:status response)))
        (is (string? (:body response)))
        (let [content (json/read-str (:body response))]
          (is (vector? content))))))

  (testing "users controller at save action"
    (let [users-bucket (atom {})]
      (users-service/set-bucket users-bucket)
      (let [response (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))]
        (is (= 201 (:status response)))
        (is (nil? (:body response)))
        (is (string? (get-in response [:headers "Location"])))))))
