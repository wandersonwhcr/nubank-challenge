(ns balance.service.users
  "Balance Users Service Layer"
  (:refer-clojure :exclude [find]))

(defrecord User [id name])

(def ^:private ^:no-doc bucket
  "User Bucket"
  nil)

(defn set-bucket
  "Configures Current User Bucket"
  [a-bucket] (def bucket a-bucket))

(defn get-bucket
  "Accesses Current User Bucket"
  [] bucket)

(defn fetch
  "Fetches Users"
  [] (vals @bucket))

(defn save
  "Saves User"
  [user] (do
    (swap! bucket assoc (:id user) user)
    (identity user)))

(defn find
  "Finds User by Identifier"
  [id] (get @bucket id))

(defn delete
  "Deletes User"
  [user] (do
    (swap! bucket dissoc (:id user))
    (identity user)))

(defn valid?
  "Validates User"
  [user] true)
