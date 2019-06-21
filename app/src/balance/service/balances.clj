;;; Balances Service Layer
(ns balance.service.balances
  (:require [balance.calculator :as bc]))

;;; Show Balance by User
(defn calculate [transactions]
  (->> transactions
    ;; Calculate Balance
    (bc/calculate)
    ;; Summary
    (assoc {} :balance)))
