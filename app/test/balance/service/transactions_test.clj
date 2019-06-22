(ns balance.service.transactions-test
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.transactions :refer :all]))

(deftest test-transactions
  (testing "Transaction Record"
    (let [id (uuid)]
      (let [transaction (->Transaction id "IN" 1.99)]
        (is (= id (:id transaction)))
        (is (= "IN" (:type transaction)))
        (is (= 1.99 (:value transaction))))))

  (testing "set-bucket"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= bucket (get-bucket)))))

  (testing "fetch"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))))

  (testing "fetch and save"
    (let [bucket (atom {}) transaction (->Transaction (uuid) "IN" 0.01)]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (is (= transaction (save transaction)))
      (is (= 1 (count (fetch)))))))
