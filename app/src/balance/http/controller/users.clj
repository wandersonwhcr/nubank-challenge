(ns balance.http.controller.users
  "Balance HTTP Controller for Users"
  (:refer-clojure :exclude [find])
  (:require [ring.util.response :refer :all]
            [balance.service.users :as users-service]
            [balance.record :refer :all]
            [balance.util :refer [uuid]]
            [balance.http.response :refer :all])
  (:import [balance.record User]))

(defn fetch
  "Index Action"
  [] (response (users-service/fetch)))

(defn save
  "Save Action"
  [request]
  (try
    (let [id (uuid)]
      (->
        (:body request)
        (assoc :id id)
        (map->User)
        (users-service/save))
      (->
        (created (str "/v1/users/" id))
        (header "X-Resource-Identifier" id)))
    (catch Exception e
      (case (:type (ex-data e))
        :user-invalid-data (unprocessable-entity (ex-data e))
        (internal-error)))))

(defn find
  "Find Action"
  [request]
  (try
    (->
      (:params request)
      (:user-id)
      (users-service/find)
      (response))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        (internal-error)))))

(defn delete
  "Delete Action"
  [request]
  (try
    (do
      (->
        (:params request)
        (:user-id)
        (users-service/delete))
      (no-content))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        (internal-error)))))
