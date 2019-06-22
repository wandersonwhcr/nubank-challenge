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
  (let [id (uuid)]
    (->
      (:body request)
      (assoc :id id)
      (map->User)
      (users-service/save))
    (created (str "/v1/users/" id))))

(defn find
  "Find Action"
  [request]
  (->
    (:params request)
    (:user-id)
    (users-service/find)
    (response)))

(defn delete
  "Delete Action"
  [request]
  (do
    (->
      (:params request)
      (:user-id)
      (users-service/delete))
    (no-content)))
