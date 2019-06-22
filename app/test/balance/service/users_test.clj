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
  (testing "save"
    (let [bucket (atom {}) user (->User (uuid) "John Doe")]
      (set-bucket bucket)
      (is (= 0 (count (fetch))))
      (is (= user (save user)))
      (is (= 1 (count (fetch))))
      (is (= user (find (:id user)))))))
