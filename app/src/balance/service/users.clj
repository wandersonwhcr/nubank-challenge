;;;; Users Service Layer
(ns balance.service.users)

;;; Users Data HashMap
(def ^:private users {})

;;; Show Users
(defn fetch [] (vals users))

;;; Check a User by Identifier
(defn check [id]
  (when (not (contains? users id))
    (throw (ex-info "Unknown User" { :type :user-not-found, :id id }))))

;;; Show a User by Identifier
(defn by [id] (do
  (check id)
  (get users id)))

;;; Save a User into Data HashMap
(defn save [data] (do
  (def users (assoc users (get data :id) data)) nil))

;;; Delete a User by Identifier
(defn delete [id] (do
  (check id)
  (def users (dissoc users id)) nil))
