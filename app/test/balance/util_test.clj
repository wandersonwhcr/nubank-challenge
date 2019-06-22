(ns balance.util-test
  (:refer-clojure :exclude [uuid?])
  (:require [clojure.test :refer :all]
            [balance.util :refer :all]))

(deftest test-util
  (testing "is"
    (is (= (uuid? "90abd9b9-81e1-45df-a0af-5ab92e13d538")))
    (is (not (uuid? "foo-bar"))))

  (testing "uuid"
    (is (string? (uuid)))
    (is (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" (uuid))))

  (testing "set"
    (let [element (set-uuid {})]
      (is (contains? element :id))
      (is (string? (:id element)))
      (is (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" (:id element)))))

  (testing "random uuid"
    (let [elementA (set-uuid {}) elementB (set-uuid {})]
      (is (not (= (:id elementA) (:id elementB))))))

  (testing "assoc"
    (let [element (set-uuid {:name "John Doe"})]
      (is (contains? element :name)))))
