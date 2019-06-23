(ns balance.service.balance-test
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.balance :as balance-service]
            [balance.record :refer :all])
  (:import [balance.record User Transaction]))

(deftest test-balance

  (testing "calculate"
    (let [transactions [(->Transaction (uuid) (uuid) "IN" 1.0)]]
      (let [balance (balance-service/calculate transactions)]
        (is (= 1.0 (:value balance))))))

  (testing "calculate without transactions"
    (let [transactions []]
      (let [balance (balance-service/calculate transactions)]
        (is (= 0.0 (:value balance)))))))
