(ns balance.http.action.users
  (:require
    [balance.util :refer :all]
    [ring.util.response :refer :all]
    [balance.http.response :refer :all]
    [balance.service.users :as users-service]))

(defn fetch []
  (response (users-service/fetch)))
