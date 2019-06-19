;;;; Users Service Layer
(ns balance.service.users
  (:require [struct.core :as st]))

;;; Users Bucket
(def ^:private users {})

;;; Users Scheme
(def ^:private scheme
  {:id [st/required st/uuid-str], :name [st/required st/string]})

;;; Show Users
(defn fetch [] (vals users))

;;; Check a User by Identifier
(defn check [id]
  (when (not (contains? users id))
    (throw (ex-info "Unknown User" {:type :user-not-found, :id id}))))

;;; Validate a User Data
(defn validate [data]
  (def validation (-> data (st/validate scheme)))
  (when (get validation 0)
    (throw (ex-info "Invalid Data" {:type :user-invalid-data, :validation (get validation 0)}))))

;;; Show a User by Identifier
(defn by [id] (do
  (check id)
  (get users id)))

;;; Save a User into Data HashMap
(defn save [data] (do
  (validate data)
  (def users (assoc users (get data :id) data)) nil))

;;; Delete a User by Identifier
(defn delete [id] (do
  (check id)
  (def users (dissoc users id)) nil))
