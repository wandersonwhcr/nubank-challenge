(ns balance.record
  "Balance Record")

(defrecord User [id name])

(defrecord Transaction [id type value])
