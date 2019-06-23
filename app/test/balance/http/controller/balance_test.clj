(ns balance.http.controller.balance-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [balance.handler :refer :all]
            [balance.service.users :as users-service]
            [balance.service.transactions :as transactions-service]
            [balance.service.balance :as balance-service]))

(defn ^:private initialize
  []
  (let [users-bucket (atom {})
        transactions-bucket (atom {})]
    (users-service/set-bucket users-bucket)
    (transactions-service/set-bucket transactions-bucket)
    (app (-> (mock/request :post "/v1/users") (mock/json-body {:name "John Doe"})))))

(defn ^:private location
  [response resource]
  (str (get-in response [:headers "Location"]) resource))

(defn ^:private decode
  [response]
  (json/read-str (:body response)))

(deftest test-transactions

  (testing "find by user"
    (let [response-user (initialize)]
      (let [response-find (app (-> (mock/request :get (location response-user "/balance"))))]
        (is (= 200 (:status response-find)))
        (is (string? (:body response-find)))
        (let [content (decode response-find)]
          (is (map? content))
          (is (= 0.0 (get content "value")))))))

  (testing "find by unknown user"
    (let [response-find (app (-> (mock/request :get "/v1/users/foobar/balance")))]
      (is (= 404 (:status response-find)))
      (is (string? (:body response-find)))
      (let [content (decode response-find)]
        (is (map? content))
        (is (= "user-unknown-identifier" (get content "type")))))))
