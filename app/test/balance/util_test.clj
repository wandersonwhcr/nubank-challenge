(ns balance.util-test
  (:require [clojure.test :refer :all]
            [balance.util :refer :all]))

(deftest test-util
  (testing "set-uuid"
    (let [element (set-uuid {})]
      (is (contains? element :id))
      (is (string? (:id element))))))
