(ns balance.balance.calculator-test
  (:refer-clojure :exclude [find])
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.balance.calculator :refer :all]
            [balance.record :refer :all])
  (:import [balance.record Transaction]))

(deftest test-calculator
  (testing "to-decimal"
    (is (= 1.99 (to-decimal (->Transaction (uuid) (uuid) "IN" 1.99))))
    (is (= -1.99 (to-decimal (->Transaction (uuid) (uuid) "OUT" 1.99)))))

  (testing "calculate"
    (let [transactions [(->Transaction (uuid) (uuid) "IN" 1.0) (->Transaction (uuid) (uuid) "IN" 2.0)]]
      (is (= 3.0 (calculate transactions)))))

  (testing "floating point arithmetic problem"
    (let [transactions [(->Transaction (uuid) (uuid) "IN" 0.1) (->Transaction (uuid) (uuid) "IN" 0.2) (->Transaction (uuid) (uuid) "OUT" 0.3)]]
      (is (= 0.0 (calculate transactions)))))

  (testing "floating point arithmetic problem"
    (let [transactions [(->Transaction (uuid) (uuid) "IN" 1.99)
                        (->Transaction (uuid) (uuid) "IN" 3.01)
                        (->Transaction (uuid) (uuid) "OUT" 1.49)]]
      (is (= 3.51 (calculate transactions)))))

  (testing "validate"
    (let [balance [(->Transaction (uuid) (uuid) "IN" 1.0)
                   (->Transaction (uuid) (uuid) "IN" 2.0)]]
      (is (validate (conj balance (->Transaction (uuid) (uuid) "IN" 3.0))))))

  (testing "validate invalid transaction"
    (let [balance [(->Transaction (uuid) (uuid) "IN" 1.0)
                   (->Transaction (uuid) (uuid) "IN" 2.0)]]
      (is (thrown-with-msg? Exception #"^Invalid Data$" (validate (conj balance (->Transaction (uuid) (uuid) "OUT" 4.0))))))))
