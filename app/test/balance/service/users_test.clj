(ns balance.service.users-test
  (:require [clojure.test :refer :all]
            [balance.service.users :refer :all]))

(deftest test-users
  (testing "set-bucket"
    (let [bucket (atom {})]
      (is (nil? (set-bucket bucket))))))
