(ns balance.service.users
  "Balance Users Service Layer"
  (:refer-clojure :exclude [find]))

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
  [] (if (empty? @bucket) [] (vals @bucket)))

(defn save
  "Saves User"
  [user]
  (do
    (swap! bucket assoc (:id user) user)
    (identity user)))

(defn has?
  "Has User by Identifier?"
  [id]
  (when (not (contains? @bucket id))
    (throw (ex-info "Unknown Identifier" {:type :user-unknown-identifier :id id}))))

(defn find
  "Finds User by Identifier"
  [id]
  (do
    (has? id)
    (get @bucket id)))

(defn delete
  "Deletes User by Identifier"
  [id] (do
    (has? id)
    (swap! bucket dissoc id)
    (identity id)))
