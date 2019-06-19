;;;; Users Service Layer
(ns balance.service.users)

;;; Users Data HashMap
(def users {})

;;; Show Users
(defn get-users [] (vals users))

;;; Show a User by Identifier
(defn get-user [id] (get users id))

;;; Save a User into Data HashMap
(defn save-user [data] (do
  ;; Keep Identifier at First Position
  (def users (assoc users (get data :id) data))
  nil))

;;; Delete a User by Identifier
(defn delete-user [id] (do
  (def users (dissoc users id))
  nil))
