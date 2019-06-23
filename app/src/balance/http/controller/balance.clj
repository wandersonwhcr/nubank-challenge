(ns balance.http.controller.balance
  "Balance HTTP Controller for Balance"
  (:require [ring.util.response :refer :all]
            [balance.service.users :as users-service]
            [balance.service.transactions :as transactions-service]
            [balance.service.balance :as balance-service]
            [balance.http.response :refer :all]))

(defn find-by-user
  "Find Action by User"
  [request]
  (try
    (->
      (:params request)
      (:user-id)
      (users-service/find)
      (transactions-service/fetch-by-user)
      (balance-service/calculate)
      (response))
    (catch Exception e
      (internal-error))))
