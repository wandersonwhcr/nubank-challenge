(ns balance.http.controller.balance-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
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

(deftest test-transactions
  (testing "find by user"
    (let [response-user (initialize)]
      (let [response-find (app (-> (mock/request :get (location response-user "/balance"))))]
        (is (= 200 (:status response-find)))))))
