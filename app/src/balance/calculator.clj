;;;; Balance Calculator
(ns balance.calculator)

;;; Coerce Transaction to Value
(defn to-decimal [transaction] (if (= (:type transaction) "in") (:value transaction) (- (:value transaction))))

;;; Calculate Balance by Transactions
(defn calculate [transactions]
  (->> transactions
    ;; Transform: "in = value positive" | "out = value negative"
    (map to-decimal)
    ;; Transform: Values to BigDecimal
    (map (fn [value] (bigdec value)))
    ;; Summation
    (reduce +)))
