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
    ; Going Back to Double
    (.doubleValue)))

(defn validate
  "Validate if Transactions Balance is Valid"
  [transactions]
  (do
    (when (> 0.0 (calculate transactions))
      (throw (ex-info "Invalid Data" {:type :calculator-invalid-data}))))
    (identity transactions))
