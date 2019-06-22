(ns balance.service.transactions-test
  (:require [clojure.test :refer :all]
            [balance.service.transactions :refer :all]))

(deftest test-transactions
  (testing "Transaction Record"
    (let [transaction (->Transaction "IN" 1.99)]
      (is (contains? transaction :type))
      (is (= "IN" (:type transaction)))
      (is (contains? transaction :value))
      (is (= 1.99 (:value transaction))))))
