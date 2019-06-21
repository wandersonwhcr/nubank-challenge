(ns balance.calculator
  "Balance Calculator")

(defn to-decimal
  "Convert one transaction to decimal value"
  [transaction] (if (= (:type transaction) "in") (:value transaction) (- (:value transaction))))

(defn calculate
  "Calculate balance using a transactions list"
  [transactions] (->> transactions
    ; Transform: "in = value positive" | "out = value negative"
    (map to-decimal)
    ; Transform: Float to BigDecimal
    (map (fn [value] (bigdec value)))
    ; Summation
    (reduce +)))
