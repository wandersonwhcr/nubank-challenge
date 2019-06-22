(ns balance.service.users-test
  (:refer-clojure :exclude [find])
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.users :refer :all]))

(deftest test-users
  (testing "User"
    (let [user (->User (uuid) "John Doe")]
      (is (= "John Doe" (:name user)))))
  (testing "set-bucket"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= bucket (get-bucket)))))
  (testing "fetch"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))))
  (testing "fetch and save"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (is (= user (save user)))
      (is (= 1 (count (fetch))))
      (is (= user (find (:id user))))))
  (testing "fetch and save multiple"
    (let [bucket (atom {}) userA (->User (uuid) "John Doe") userB (->User (uuid) "João da Silva")]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (save userA)
      (save userB)
      (is (= 2 (count (fetch))))
      (is (= userA (find (:id userA))))
      (is (= userB (find (:id userB))))))
  (testing "save and delete"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (save user)
      (is (= 1 (count (fetch))))
      (is (= user (delete user)))
      (is (= 0 (count (fetch))))))
  (testing "valid"
    (is (valid? (->User (uuid) "John Doe")))))
