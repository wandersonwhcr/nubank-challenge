;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [balance.util :refer :all]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;;; Fetch Transactions by User
(defn fetchByUser [user] (->> (vals @transactions)
  ; Only Transactions for User
  (filter (fn [transaction] (= (:id user) (:userId transaction))))
  ; Remover User Identifier from Element
  (map (fn [transaction] (dissoc transaction :userId)))))

;;; Save Transactions
(defn saveByUser [user data] (->> data
  ; Configure User Identifier
  (merge {:userId (:id user)})
  ; Store Transaction
  (swap! transactions assoc (:id data)))
  ; Saved Data
  (identity data));
