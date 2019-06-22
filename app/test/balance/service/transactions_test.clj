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
        (is (= 1.99 (:value transaction)))))))
