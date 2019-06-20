;;; Balances Service Layer
(ns balance.service.balances)

;;; Show Balance by User
(defn calculate [transactions]
  (->> transactions
    ;; Transform: "in = value positive" | "out = value negative"
    (map (fn [transaction] (if (= (:type transaction) "in") (:value transaction) (- (:value transaction)))))
    ;; Transform: Values to BigDecimal
    (map (fn [value] (bigdec value)))
    ;; Summation
    (reduce +)
    ;; Summary
    (assoc {} :total)))
