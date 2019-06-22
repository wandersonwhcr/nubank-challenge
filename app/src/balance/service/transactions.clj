(ns balance.service.transactions
  "Balance Transactions Service Layer"
  (:refer-clojure :exclude [find])
  (:require [json-schema.core :as json]))

(def ^:private ^:no-doc bucket
  "Transaction Bucket"
  nil)

(def ^:private schema
  "Transaction Schema Validation"
  (slurp "src/balance/schema/transactions.json"))

(defn set-bucket
  "Configures Current Transaction Bucket"
  [a-bucket] (def bucket a-bucket))

(defn get-bucket
  "Accesses Current Transaction Bucket"
  [] bucket)

(defn fetch
  "Fetches Transactions"
  [] (vals @bucket))

(defn fetch-by-user
  "Fetches Transactions by User"
  [user]
  (filter #(= (:id user) (:user-id %)) (vals @bucket)))

(defn validate
  "Validates Transaction"
  [transaction]
  (try
    (json/validate schema transaction)
    (catch Exception e (->>
      (ex-data e)
      (merge {:type :transaction-invalid-data})
      (ex-info "Invalid Data")
      (throw)))))

(defn save
  "Saves Transaction"
  [transaction]
  (do
    (validate transaction)
    (swap! bucket assoc (:id transaction) transaction)
    (identity transaction)))

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
    (when (not (= (:id user) (:user-id transaction)))
      (throw (ex-info "Unknown Identifier" {:type :transaction-unknown-identifier :id id})))
    (delete id)))
