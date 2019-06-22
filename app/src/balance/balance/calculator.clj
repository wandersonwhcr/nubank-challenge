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
  [transactions] (->> transactions
    ; Convert Records to Decimals
    (map to-decimal)
    ; Using BigDecimals to avoid Floating Point Problems
    (map bigdec)
    ; Summation
    (reduce +)
    ; Going Back to Float
    (.floatValue)))

(defn validate
  "Validate if Transaction is Valid into Balance"
  [transactions current]
  (do
    (when (> 0.0 (calculate (conj transactions current)))
      (throw (ex-info "Invalid Data" {:type :calculator-invalid-data :id (:id current)}))))
    (identity current))
