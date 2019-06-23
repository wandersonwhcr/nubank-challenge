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

  (testing "nvalid ->User"
    (is (thrown-with-msg? Exception #"^Invalid Data$" (->User "" ""))))

  (testing "invalid map->User"
    (is (thrown-with-msg? Exception #"^Invalid Data$" (map->User {:id "" :name ""})))
    (is (thrown-with-msg? Exception #"^Invalid Data$" (map->User {:id (uuid) :name "John Doe" :email "foo@bar.com"}))))

  (testing "Transaction"
    (let [id (uuid) user-id (uuid)]
      (let [transaction (->Transaction id user-id "IN" 1.99)]
        (is (= id (:id transaction)))
        (is (= user-id (:user-id transaction)))
        (is (= "IN" (:type transaction)))
        (is (= 1.99 (:value transaction)))))))
