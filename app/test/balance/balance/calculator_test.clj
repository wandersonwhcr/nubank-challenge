(ns balance.balance.calculator-test
  (:require [clojure.test :refer :all]
            [balance.balance.calculator :refer :all]
            [balance.service.transactions :refer :all]))

(deftest test-calculator
  (testing "to-decimal"
    (is (= 1.99 (to-decimal (->Transaction "IN" 1.99))))
    (is (= -1.99 (to-decimal (->Transaction "OUT" 1.99)))))

  (testing "calculate"
    (let [transactions [(->Transaction "IN" 1.0) (->Transaction "IN" 2.0)]]
      (is (= 3.0 (calculate transactions)))))

  (testing "floating point arithmetic problem"
    (let [transactions [(->Transaction "IN" 0.1) (->Transaction "IN" 0.2) (->Transaction "OUT" 0.3)]]
      (is (= 0.0 (calculate transactions))))))
