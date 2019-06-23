(ns balance.service.transactions
  "Balance Transactions Service Layer"
  (:refer-clojure :exclude [find])
  (:require [balance.balance.calculator :as calculator]))

(def ^:private ^:no-doc bucket
  "Transaction Bucket"
  (atom {}))

(def ^:private locker
  "Transaction Locker"
  (Object.))

(defn set-bucket
  "Configures Current Transaction Bucket"
  [a-bucket] (def bucket a-bucket))

(defn get-bucket
  "Accesses Current Transaction Bucket"
  [] bucket)

(defn fetch
  "Fetches Transactions"
  [] (if (empty? @bucket) [] (vals @bucket)))

(defn fetch-by-user
  "Fetches Transactions by User"
  [user]
  (filter #(= (:id user) (:user-id %)) (vals @bucket)))

(defn save
  "Saves Transaction"
  [transaction]
  (do
    (swap! bucket assoc (:id transaction) transaction)
    (identity transaction)))

(defn save-by-user
  "Saves Transaction by User"
  [user transaction]
  (locking locker
    (let [transactions (fetch-by-user user)]
      (calculator/validate (conj transactions transaction))
      (save transaction))))

(defn has?
  "Has User by Identifier?"
  [id]
  (when (not (contains? @bucket id))
    (throw (ex-info "Unknown Identifier" {:type :transaction-unknown-identifier :id id}))))

(defn find
  "Finds Transaction by Identifier"
  [id]
  (do
    (has? id)
    (get @bucket id)))

(defn find-by-user
  "Finds Transaction by User by Identifier"
  [user id]
  (let [transaction (find id)]
    (when (not (= (:id user) (:user-id transaction)))
      (throw (ex-info "Unknown Identifier" {:type :transaction-unknown-identifier :id id})))
    (identity transaction)))

(defn delete
  "Deletes Transaction by Identifier"
  [id]
  (do
    (has? id)
    (swap! bucket dissoc id)
    (identity id)))

(defn delete-by-user
  "Deletes Transaction by User by Identifier"
  [user id]
  (let [transaction (find-by-user user id)]
    (locking locker
      (let [transactions (fetch-by-user user)]
        (calculator/validate (filter #(not (= (:id transaction) (:id %))) transactions))
        (delete id)))))
