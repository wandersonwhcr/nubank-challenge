;;;; Users Service Layer
(ns balance.service.users)

;;; Users Data HashMap
(def users {})

;;; Show Users
(defn fetch [] (vals users))

;;; Show a User by Identifier
(defn by [id] (get users id))

;;; Save a User into Data HashMap
(defn save [data] (do
  (def users (assoc users (get data :id) data))
  nil))

;;; Delete a User by Identifier
(defn delete [id] (do
  (def users (dissoc users id))
  nil))
