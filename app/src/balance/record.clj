(ns balance.record
  "Balance Record"
  (:require [json-schema.core :as json]))

(defrecord User [id name])

(def ^:private user-schema
  "User Schema Validation"
  (slurp "src/balance/schema/users.json"))

(defn ->User
  "User Constructor"
  [id name] (map->User {:id id :name name}))

(defn map->User
  "User Constructor by Map"
  [data]
  (do
    (try
      (json/validate user-schema data)
      (catch Exception e (->>
        (ex-data e)
        (merge {:type :user-invalid-data})
        (ex-info "Invalid Data")
        (throw))))
    (User. (:id data) (:name data))))

(defrecord Transaction [id user-id type value])

(def ^:private transaction-schema
  "Transaction Schema Validation"
  (slurp "src/balance/schema/transactions.json"))

(defn ->Transaction
  "Transaction Constructor"
  [id user-id type value] (map->Transaction {:id id :user-id user-id :type type :value value}))

(defn map->Transaction
  "Transaction Constructor by Map"
  [data]
  (do
    (try
      (json/validate transaction-schema data)
      (catch Exception e (->>
        (ex-data e)
        (merge {:type :transaction-invalid-data})
        (ex-info "Invalid Data")
        (throw))))))
