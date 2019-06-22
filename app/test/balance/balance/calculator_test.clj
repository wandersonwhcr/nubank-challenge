(ns balance.balance.calculator-test
  (:require [clojure.test :refer :all]
            [balance.balance.calculator :refer :all]))

(deftest test-calculator
  (testing "to-decimal"
    (is (= 1 (to-decimal)))))
