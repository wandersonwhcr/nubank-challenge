(ns balance.service.transactions-test
  (:require [clojure.test :refer :all]
            [balance.service.transactions :refer :all]))

(deftest test-transactions
  (testing "Transaction Record"
    (let [transaction (->Transaction "IN")]
      (is (contains? transaction :type))
      (is (= "IN" (:type transaction))))))
