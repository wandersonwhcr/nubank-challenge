;;;; Users Service Layer
(ns balance.service.users
  (:refer-clojure :exclude [find])
  (:require
    [struct.core :as st]
    [json-schema.core :as json]))

;;; Users Bucket
(def ^:private users {})

;;; Users Scheme
(def ^:private scheme
  {:id [st/required st/uuid-str], :name [st/required st/string]})

;;; Users JSON Schema
(def ^:private schema {:type "object"
                       :properties {:id {:type "string" :pattern "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"}
                                    :name {:type "string"}}
                       :additionalProperties false
                       :required [:id :name]})

;;; Check a User by Identifier
(defn ^:private has? [id]
  (when (not (contains? users id))
    (throw (ex-info "Unknown User" {:type :user-not-found, :id id}))))

;;; Validate a User Data
(defn ^:private valid? [data]
  (try
    (json/validate schema data)
    (catch Exception e
;      (throw (ex-info "Invalid Data" {:type :user-invalid-data :validation (ex-data e)})))))
      (throw (ex-info "Invalid Data" (merge {:type :user-invalid-data} (ex-data e)))))))

;;; Show Users
(defn fetch [] (vals users))

;;; Show a User by Identifier
(defn find [id] (do
  (has? id)
  (get users id)))

;;; Save a User into Data HashMap
(defn save [data] (do
  (valid? data)
  (def users (assoc users (get data :id) data)) nil))

;;; Delete a User by Identifier
(defn delete [id] (do
  (has? id)
  (def users (dissoc users id)) nil))
