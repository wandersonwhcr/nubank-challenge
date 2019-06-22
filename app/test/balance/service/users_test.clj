(ns balance.service.users-test
  (:require [clojure.test :refer :all]
            [balance.service.users :refer :all]))

(deftest test-users
  (testing "User"
    (let [user (->User "John Doe")]
      (is (= "John Doe" (:name user)))))
  (testing "set-bucket"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= bucket (get-bucket)))))
  (testing "fetch"
    (let [bucket (atom {})]
      (set-bucket bucket)
      (is (= 0 (count (fetch)))))))
