(ns balance.http.controller.users-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            [balance.handler :refer :all]))

(deftest test-users
  (testing "users controller at index action"
    (let [response (app (mock/request :get "/v1/users"))]
      (is (= 200 (:status response))))))
