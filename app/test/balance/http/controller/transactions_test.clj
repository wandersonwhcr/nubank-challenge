(ns balance.http.controller.transactions-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [balance.handler :refer :all]
            [balance.service.users :as users-service]
            [balance.service.transactions :as transactions-service]))

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
  (testing "fetch"
    (let [response-user (initialize)]
      (let [response-fetch (app (-> (mock/request :get (location response-user "/transactions"))))]
        (is (= 200 (:status response-fetch))
        (is (string? (:body response-fetch))
        (let [content (json/read-str (:body response-fetch))]
          (is (vector? content))
          (is (= 0 (count content))))))))))
