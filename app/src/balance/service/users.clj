(ns balance.service.users
  "Balance Users Service Layer")

(defrecord User [id name])

(def ^:private ^:no-doc bucket
  "User Bucket"
  nil)

(defn set-bucket
  "Configures Current User Bucket"
  [a-bucket] (def bucket a-bucket))

(defn get-bucket
  "Access Current User Bucket"
  [] bucket)

(defn fetch
  "Fetch Users"
  [] (vals @bucket))

(defn save
  "Save User"
  [user] (do
    (swap! bucket assoc (:id user) user)
    (identity user)))
