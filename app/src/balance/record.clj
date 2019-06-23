(ns balance.record
  "Balance Record"
  (:require [json-schema.core :as json]))

(defrecord User [id name])

(def ^:private user-schema
  "User Schema Validation"
  (slurp "src/balance/schema/users.json"))

(defn map->User
  [data] (do
    (try
      (json/validate user-schema data)
      (catch Exception e (->>
        (ex-data e)
        (merge {:type :user-invalid-data})
        (ex-info "Invalid Data")
        (throw))))
    (User. (:id data) (:name data))))

(defrecord Transaction [id user-id type value])
