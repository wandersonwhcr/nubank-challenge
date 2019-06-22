(ns balance.balance.calculator
  "Balance Calculator")

(defn to-decimal
  "Converts a Transaction Record to Decimal"
  [transaction]
  (case (:type transaction)
    "IN" (:value transaction)
    "OUT" (- (:value transaction))))

(defn calculate
  "Calculates Balance for Transactions"
  [] 0)
