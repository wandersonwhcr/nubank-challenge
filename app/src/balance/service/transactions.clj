(ns balance.service.transactions
  "Balance Transactions Service Layer"
  (:refer-clojure :exclude [find])
  (:require [json-schema.core :as json]))

(defrecord Transaction [id type value])

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

(defn delete
  "Deletes Transaction by Identifier"
  [id]
  (do
    (has? id)
    (swap! bucket dissoc id)
    (identity id)))
