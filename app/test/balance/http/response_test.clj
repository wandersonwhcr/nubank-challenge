(ns balance.http.response-test
  (:require [clojure.test :refer :all]
            [balance.http.response :refer :all]))

(deftest test-response
  (testing "no-content response"
    (let [response (no-content)]
      (is (= 204 (response :status)))
      (is (contains? response :headers))
      (is (contains? response :body))))
  (testing "unprocessable-entity response without body"
    (let [response (unprocessable-entity)]
      (is (= 422 (response :status)))))
  (testing "unprocessable-entity response with body"
    (let [response (unprocessable-entity {:message "Something Happened"})]
      (is (= 422 (response :status)))
      (is (contains? response :headers))
      (is (contains? response :body))
      (is (= "Something Happened" (get-in response [:body :message]))))))
