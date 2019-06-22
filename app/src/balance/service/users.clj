(ns balance.service.users
  "Balance Users Service Layer")

(defrecord User [name])

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
  [] [])

(defn save
  "Save User"
  [user] user)
