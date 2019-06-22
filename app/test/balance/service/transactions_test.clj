(ns balance.service.transactions-test
  (:refer-clojure :exclude [find])
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.transactions :refer :all]
            [balance.record :refer :all])
  (:import [balance.record User Transaction]))

(deftest test-transactions

  (testing "set-bucket"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= bucket (get-bucket)))))

  (testing "fetch"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))))

  (testing "fetch and save"
    (let [bucket (atom {}) transaction (->Transaction (uuid) (uuid) "IN" 0.01)]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (is (= transaction (save transaction)))
      (is (= 1 (count (fetch))))
      (is (= transaction (find (:id transaction))))))

  (testing "fetch and save multiple"
    (let [bucket (atom {}) transactionA (->Transaction (uuid) (uuid) "IN" 0.01) transactionB (->Transaction (uuid) (uuid) "OUT" 0.02)]
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
      (save (->Transaction id (uuid) "IN" 1.0))
      (is (= 1 (count (fetch))))
      (is (= id (delete id)))
      (is (= 0 (count (fetch))))))

  (testing "validate"
    (is (validate (->Transaction (uuid) (uuid) "IN" 1.0)))
    (is (thrown? Exception (validate (->Transaction "" "" "" 0))))
    (is (thrown? Exception (validate (->Transaction "" (uuid) "IN" 1.0))))
    (is (thrown? Exception (validate (->Transaction (uuid) "" "IN" 1.0))))
    (is (thrown? Exception (validate (->Transaction (uuid) (uuid) "" 1.0))))
    (is (thrown? Exception (validate (->Transaction (uuid) (uuid) "IN" 0))))
    (is (thrown-with-msg? Exception #"^Invalid Data$" (validate (->Transaction "" "" "" 0)))))

  (testing "save with invalid data"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (thrown-with-msg? Exception #"^Invalid Data$" (save (->Transaction "" "" "" 0))))))

  (testing "find with unknown identifier"
    (let [bucket (atom {}) transaction (->Transaction (uuid) (uuid) "IN" 1.0)]
      (set-bucket bucket)
      (save transaction)
      (is (find (:id transaction)))
      (is (thrown-with-msg? Exception #"^Unknown Identifier$" (find (uuid))))))

  (testing "delete with unknown identifier"
    (let [bucket (atom {}) transaction (->Transaction (uuid) (uuid) "IN" 1.0)]
      (set-bucket bucket)
      (save transaction)
      (is (delete (:id transaction)))
      (is (thrown-with-msg? Exception #"^Unknown Identifier$" (delete (uuid))))))

  (testing "fetch by user"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (let [transactionA (->Transaction (uuid) (:id user) "IN" 1.0)
            transactionB (->Transaction (uuid) (:id user) "IN" 2.0)
            transactionC (->Transaction (uuid) (uuid) "IN" 3.0)]
        (set-bucket bucket)
        (save transactionA)
        (save transactionB)
        (save transactionC)
        (is (= 2 (count (fetch-by-user user))))
        (is (some #(= transactionA %) (fetch-by-user user)))
        (is (some #(= transactionB %) (fetch-by-user user)))
        (is (not (some #(= transactionC %) (fetch-by-user user)))))))

  (testing "find by user"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (let [transactionA (->Transaction (uuid) (:id user) "IN" 1.0)
            transactionB (->Transaction (uuid) (:id user) "IN" 2.0)]
        (set-bucket bucket)
        (save transactionA)
        (save transactionB)
        (is (= transactionA (find-by-user user (:id transactionA)))))))

  (testing "find by user with unknown identifier"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (save (->Transaction (uuid) (:id user) "IN" 1.0))
      (is (thrown-with-msg? Exception #"^Unknown Identifier$" (find-by-user user (uuid))))))

  (testing "delete by user"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (let [transactionA (->Transaction (uuid) (:id user) "IN" 1.0)
            transactionB (->Transaction (uuid) (:id user) "IN" 2.0)]
        (set-bucket bucket)
        (save transactionA)
        (save transactionB)
        (is (= (:id transactionA) (delete-by-user user (:id transactionA))))
        (is (= (:id transactionB) (delete-by-user user (:id transactionB))))
        (is (= 0 (count (fetch-by-user user)))))))

  (testing "save by user"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (let [transaction (->Transaction (uuid) (:id user) "IN" 1.0)]
        (set-bucket bucket)
        (is (= transaction (save-by-user user transaction)))))))
