(ns balance.http.controller.transactions
  "Balance HTTP Controller for Transactions"
  (:refer-clojure :exclude [find])
  (:require [ring.util.response :refer :all]
            [balance.service.users :as users-service]
            [balance.service.transactions :as transactions-service]
            [balance.record :refer :all]
            [balance.util :refer [uuid]]
            [balance.http.response :refer :all]))

(defn fetch-by-user
  "Fetch Action by User"
  [request]
  (try
    (->
      (:params request)
      (:user-id)
      (users-service/find)
      (transactions-service/fetch-by-user)
      (response))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        (internal-error)))))

(defn save-by-user
  "Save Action by User"
  [request]
  (try
    (let [id (uuid)]
      (let [user (users-service/find (get-in request [:params :user-id]))]
        (let [data (-> (:body request) (assoc :id id) (assoc :user-id (:id user)))]
          (let [transaction (map->Transaction data)]
            (transactions-service/save-by-user user transaction)
            (->
              (created (str "/v1/users/" (:id user) "/transactions/" id))
              (header "X-Resource-Identifier" id))))))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        :calculator-invalid-data (unprocessable-entity (ex-data e))
        (internal-error)))))

(defn find-by-user
  "Find Action by User"
  [request]
  (try
    (let [user (users-service/find (get-in request [:params :user-id]))]
      (let [transaction (transactions-service/find-by-user user (get-in request [:params :transaction-id]))]
        (response transaction)))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        :transaction-unknown-identifier (not-found (ex-data e))
        (internal-error)))))

(defn delete-by-user
  "Delete Action by User"
  [request]
  (try
    (let [user (users-service/find (get-in request [:params :user-id]))]
      (let [transaction (transactions-service/delete-by-user user (get-in request [:params :transaction-id]))]
        (no-content)))
    (catch Exception e
      (case (:type (ex-data e))
        :user-unknown-identifier (not-found (ex-data e))
        :transaction-unknown-identifier (not-found (ex-data e))
        (internal-error)))))
