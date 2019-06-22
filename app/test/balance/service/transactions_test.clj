(ns balance.service.transactions-test
  (:refer-clojure :exclude [find])
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
      (is (= 1 (count (fetch))))
      (is (= transaction (find (:id transaction))))))

  (testing "fetch and save multiple"
    (let [bucket (atom {}) transactionA (->Transaction (uuid) "IN" 0.01) transactionB (->Transaction (uuid) "OUT" 0.02)]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (save transactionA)
      (save transactionB)
      (is (= 2 (count (fetch))))
      (is (= transactionA (find (:id transactionA))))
      (is (= transactionB (find (:id transactionB))))))

  (testing "save and delete"
    (let [bucket (atom {}) id (uuid)]
      (set-bucket bucket)
      (save (->Transaction id "IN" 1.0))
      (is (= 1 (count (fetch))))
      (is (= id (delete id)))
      (is (= 0 (count (fetch))))))

  (testing "validate"
    (is (validate (->Transaction (uuid) "IN" 1.0)))
    (is (thrown? Exception (validate (->Transaction "" "" 0))))
    (is (thrown? Exception (validate (->Transaction "" "IN" 1.0))))
    (is (thrown? Exception (validate (->Transaction (uuid) "" 1.0))))
    (is (thrown? Exception (validate (->Transaction (uuid) "IN" 0))))
    (is (thrown-with-msg? Exception #"^Invalid Data$" (validate (->Transaction "" "" 0)))))

  (testing "save with invalid data"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (thrown-with-msg? Exception #"^Invalid Data$" (save (->Transaction "" "" 0)))))))
