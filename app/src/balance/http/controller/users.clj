(ns balance.http.controller.users
  "Balance HTTP Controller for Users"
  (:require [ring.util.response :refer :all]
            [balance.service.users :as users-service]))

(defn index
  "Index Action"
  [] (response (users-service/fetch)))
