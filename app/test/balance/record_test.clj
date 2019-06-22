(ns balance.record-test
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.record :refer :all])
  (:import [balance.record User Transaction]))

(deftest test-record

  (testing "User"
    (let [id (uuid)]
      (let [user (->User id "John Doe")]
        (is (= id (:id user)))
        (is (= "John Doe" (:name user))))))

  (testing "Transaction"
    (let [id (uuid)]
      (let [transaction (->Transaction id "IN" 1.99)]
        (is (= id (:id transaction)))
        (is (= "IN" (:type transaction)))
        (is (= 1.99 (:value transaction)))))))
