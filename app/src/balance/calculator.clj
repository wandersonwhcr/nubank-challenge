;;;; Balance Calculator
(ns balance.calculator)

;;; Calculate Balance by Transactions
(defn calculate [transactions]
  (->> transactions
    ;; Transform: "in = value positive" | "out = value negative"
    (map (fn [transaction] (if (= (:type transaction) "in") (:value transaction) (- (:value transaction)))))
    ;; Transform: Values to BigDecimal
    (map (fn [value] (bigdec value)))
    ;; Summation
    (reduce +)))
