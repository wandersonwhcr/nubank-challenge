(ns balance.util-test
  (:require [clojure.test :refer :all]
            [balance.util :refer :all]))

(deftest test-util
  (testing "set"
    (let [element (set-uuid {})]
      (is (contains? element :id))
      (is (string? (:id element)))
      (is (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" (:id element)))))
  (testing "random uuid"
    (let [elementA (set-uuid {}) elementB (set-uuid {})]
      (is (not (= (:id elementA) (:id elementB)))))))
