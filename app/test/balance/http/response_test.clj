(ns balance.http.response-test
  (:require [clojure.test :refer :all]
            [balance.http.response :refer :all]))

(deftest test-response
  (testing "no-content response"
    (let [response (no-content)]
      (is (= 204 (:status response)))
      (is (contains? response :headers))
      (is (contains? response :body))))

  (testing "unprocessable-entity response without body"
    (let [response (unprocessable-entity)]
      (is (= 422 (:status response)))
      (is (contains? response :headers))
      (is (contains? response :body))))

  (testing "unprocessable-entity response with body"
    (let [response (unprocessable-entity {:message "Something Happened"})]
      (is (= 422 (:status response)))
      (is (contains? response :headers))
      (is (contains? response :body))
      (is (= "Something Happened" (get-in response [:body :message])))))

  (testing "internal-error response"
    (let [response (internal-error)]
      (is (= 500 (:status response)))
      (is (contains? response :headers))
      (is (contains? response :body)))))
