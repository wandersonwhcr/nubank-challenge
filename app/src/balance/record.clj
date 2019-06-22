(ns balance.record
  "Balance Record")

(defrecord User [id name])

(defrecord Transaction [id user-id type value])
