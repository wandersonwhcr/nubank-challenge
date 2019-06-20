(ns balance.http.action.transactions
  (:require
    [ring.util.response :refer :all]
    [balance.service.users :as users-service]
    [balance.service.transactions :as transactions-service]))

(defn fetch [user-id] (->
  user-id
  users-service/find
  transactions-service/fetchByUser
  response))

(defn save [user-id] (->
  user-id
  users-service/find
  response))
