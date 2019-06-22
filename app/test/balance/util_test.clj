(ns balance.util-test
  (:require [clojure.test :refer :all]
            [balance.util :refer :all]))

(deftest test-util
  (testing "uuid"
    (is (string? (uuid))))
  (testing "set"
    (let [element (set-uuid {})]
      (is (contains? element :id))
      (is (string? (element :id)))
      (is (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" (element :id)))))
  (testing "random uuid"
    (let [elementA (set-uuid {}) elementB (set-uuid {})]
      (is (not (= (elementA :id) (elementB :id))))))
  (testing "assoc"
    (let [element (set-uuid {:name "John Doe"})]
      (is (contains? element :name)))))
