(ns balance.service.users
  "Balance Users Service Layer"
  (:refer-clojure :exclude [find])
  (:require [json-schema.core :as json]))

(defrecord User [id name])

(def ^:private ^:no-doc bucket
  "User Bucket"
  nil)

(def ^:private schema
  "User Schema Validation"
  (slurp "src/balance/schema/users.json"))

(defn set-bucket
  "Configures Current User Bucket"
  [a-bucket] (def bucket a-bucket))

(defn get-bucket
  "Accesses Current User Bucket"
  [] bucket)

(defn fetch
  "Fetches Users"
  [] (vals @bucket))

(defn validate
  "Validates User"
  [user]
  (try
    (json/validate schema user)
    (catch Exception e (->>
      (ex-data e)
      (merge {:type :user-invalid-data})
      (ex-info "Invalid Data")
      (throw)))))

(defn save
  "Saves User"
  [user] (do
    (validate user)
    (swap! bucket assoc (:id user) user)
    (identity user)))

(defn find
  "Finds User by Identifier"
  [id]
  (do
    (when (not (contains? @bucket id))
      (->>
        {:type :user-unknown-identifier :id id}
        (ex-info "Unknown Identifier")
        (throw)))
    (get @bucket id)))

(defn delete
  "Deletes User"
  [user] (do
    (swap! bucket dissoc (:id user))
    (identity user)))
