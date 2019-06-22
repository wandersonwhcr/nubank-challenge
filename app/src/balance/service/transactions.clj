(ns balance.service.transactions
  "Balance Transactions Service Layer")

(defrecord Transaction [id type value])

(def ^:private ^:no-doc bucket
  "Transaction Bucket"
  nil)

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
