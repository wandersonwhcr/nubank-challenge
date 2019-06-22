(ns balance.http.controller.home-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [balance.handler :refer :all]))

(deftest test-home
  (testing "home controller at index action"
    (let [response (app (mock/request :get "/"))]
      (is (= 200 (:status response)))
      (is (string? (:body response))))))
