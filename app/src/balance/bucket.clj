(ns ^:no-doc balance.bucket
  "Balance Buckets")

(def users
  "Users Bucket"
  (atom {}))

(def transactions
  "Transactions Bucket"
  (atom {}))
