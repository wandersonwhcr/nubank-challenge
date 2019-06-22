(ns balance.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [balance.handler :refer :all]))

(deftest test-app
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))
      (is (nil? (:body response))))))
