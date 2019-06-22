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

(defn save
  "Saves Transaction"
  [transaction]
  (do
    (swap! bucket assoc (:id transaction) transaction)
    (identity transaction)))

(defn find
  "Finds Transaction by Identifier"
  [id] (get @bucket id))

(defn delete
  "Deletes Transaction by Identifier"
  [id]
  (do
    (swap! bucket dissoc id)
    (identity id)))

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
