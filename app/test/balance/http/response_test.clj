(ns balance.http.response-test
  (:require [clojure.test :refer :all]
            [balance.http.response :refer :all]))

(deftest test-response
  (testing "no-content response"
    (let [response (no-content)]
      (is (= 204 (response :status))))))
