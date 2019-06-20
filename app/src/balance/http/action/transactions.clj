(ns balance.http.action.transactions
  (:refer-clojure :exclude [find])
  (:require
    [ring.util.response :refer :all]
    [balance.util :refer :all]
    [balance.http.response :refer :all]
    [balance.service.users :as users-service]
    [balance.service.transactions :as transactions-service]))

(defn fetch [user-id] (-> user-id
  (users-service/find)
  (transactions-service/fetchByUser)
  (response)))

(defn save [user-id data] (-> user-id
  (users-service/find)
  (transactions-service/saveByUser (set-uuid data))
  (saved)))

(defn find [user-id transaction-id] (-> user-id
  (users-service/find)
  (transactions-service/findByUser transaction-id)
  (response)))

(defn delete [user-id transaction-id] (-> user-id
  (users-service/find)
  (transactions-service/deleteByUser transaction-id)
  (no-content)))
