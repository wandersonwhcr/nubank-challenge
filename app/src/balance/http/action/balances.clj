(ns balance.http.action.balances
  (:refer-clojure :exclude [find])
  (:require
    [ring.util.response :refer :all]
    [balance.http.response :refer :all]
    [balance.service.users :as users-service]
    [balance.service.balances :as balances-service]))

(defn find [request]
  (try
    (-> request
      (get-in [:params :user-id])
      (users-service/find)
      (balances-service/findByUser)
      (response))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        (internal-error)))))
