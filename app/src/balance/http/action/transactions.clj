(ns balance.http.action.transactions
  (:require
    [ring.util.response :refer :all]
    [balance.service.users :as users-service]
    [balance.service.transactions :as transactions-service]))

(defn fetch [user-id] (do
  (users-service/find user-id)
  (response (transactions-service/fetch))))
