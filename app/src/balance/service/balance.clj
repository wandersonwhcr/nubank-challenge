(ns balance.service.balance
  "Balance Service Layer"
  (:require [balance.balance.calculator :as calculator]))

(defn calculate
  "Calculates Balance with Transactions"
  [transactions]
  (assoc {} :value (calculator/calculate transactions)))
