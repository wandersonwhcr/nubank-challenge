(ns balance.balance.calculator-test
  (:require [clojure.test :refer :all]
            [balance.balance.calculator :refer :all]
            [balance.service.transactions :refer :all]))

(deftest test-calculator
  (testing "to-decimal"
    (is (= 1.99 (to-decimal (->Transaction "IN" 1.99))))
    (is (= -1.99 (to-decimal (->Transaction "OUT" 1.99)))))
  (testing "calculate"
    (is (= 0 (calculate)))))
