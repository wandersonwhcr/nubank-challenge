(ns balance.http.action.transactions
  (:refer-clojure :exclude [find])
  (:require
    [ring.util.response :refer :all]
    [balance.util :refer :all]
    [balance.http.response :refer :all]
    [balance.service.users :as users-service]
    [balance.service.transactions :as transactions-service]))

(defn fetch [user-id]
  (try
    (-> user-id
      (users-service/find)
      (transactions-service/fetchByUser)
      (response))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        (internal-error)))))

(defn save [user-id data]
  (try
    (-> user-id
      (users-service/find)
      (transactions-service/saveByUser (set-uuid data))
      (saved))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        :transaction-invalid-data (unprocessable-entity (ex-data e))
        :transaction-invalid-balance (unprocessable-entity (ex-data e))
        (internal-error)))))

(defn find [user-id transaction-id]
  (try
    (-> user-id
      (users-service/find)
      (transactions-service/findByUser transaction-id)
      (response))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        :transaction-not-found (not-found (ex-data e))
        (internal-error)))))

(defn delete [user-id transaction-id]
  (try
    (-> user-id
      (users-service/find)
      (transactions-service/deleteByUser transaction-id)
      (no-content))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        :transaction-not-found (not-found (ex-data e))
        :transaction-invalid-balance (unprocessable-entity (ex-data e))
        (internal-error)))))
