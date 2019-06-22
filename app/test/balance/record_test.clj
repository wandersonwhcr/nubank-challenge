(ns balance.record-test
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.record :refer :all])
  (:import [balance.record User]))

(deftest test-record
  (testing "User"
    (let [id (uuid)]
      (let [user (->User id "John Doe")]
        (is (= id (:id user)))
        (is (= "John Doe" (:name user)))))))
