;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [balance.util :refer :all]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;; Check a Transaction by Identifier
(defn ^:private has? [id]
  (when (not (contains? @transactions id))
    (throw (ex-info "Unknown Transaction" {:type :transaction-not-found, :id id}))))

;; Check a Transaction by User
(defn ^:private hasByUser? [user id]
  (when (has? id)
    (when (not (= (:id user) (:userId (get @transactions id)))))
      (throw (ex-info "Unknown Transaction" {:type :transaction-not-found, :id id}))))

;;; Fetch Transactions by User
(defn fetchByUser [user] (->> (vals @transactions)
  ; Only Transactions for User
  (filter (fn [transaction] (= (:id user) (:userId transaction))))
  ; Remove User Identifier from Element
  (map (fn [transaction] (dissoc transaction :userId)))))

;;; Save Transactions
(defn saveByUser [user data] (->> data
  ; Configure User Identifier
  (merge {:userId (:id user)})
  ; Store Transaction
  (swap! transactions assoc (:id data)))
  ; Saved Data
  (identity data));

;;; Show a Transaction by User by Identifier
(defn findByUser [user id] (do
  (hasByUser? user id)
  (dissoc (get @transactions id) :userId)))
