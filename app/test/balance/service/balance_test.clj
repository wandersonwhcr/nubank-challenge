(ns balance.service.balance-test
  (:require [clojure.test :refer :all]
            [balance.util :refer [uuid]]
            [balance.service.balance :refer :all]
            [balance.record :refer :all])
  (:import [balance.record User Transaction]))
