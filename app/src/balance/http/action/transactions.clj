(ns balance.http.action.transactions
  (:require
    [ring.util.response :refer :all]
    [balance.service.users :as users-service]))

(defn fetch [user-id] (do
  (users-service/find user-id)
  (response [])))
