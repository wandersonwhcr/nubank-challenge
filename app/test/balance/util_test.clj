(ns balance.util-test
  (:require [clojure.test :refer :all]
            [balance.util :refer :all]))

(deftest test-util
  (testing "set-uuid"
    (let [element (set-uuid {})]
      (is (contains? element :id))
      (is (string? (:id element)))
      (is (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" (:id element))))))
