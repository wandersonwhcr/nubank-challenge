(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [balance.http.controller.home :as home]
            [balance.http.controller.users :as users]
            [balance.http.controller.transactions :as transactions]))

(defroutes app-routes
  (GET "/" [] (home/index))

  (GET "/v1/users" [] (users/fetch))
  (POST "/v1/users" request (users/save request))
  (GET "/v1/users/:user-id" request (users/find request))
  (DELETE "/v1/users/:user-id" request (users/delete request))

  (GET "/v1/users/:user-id/transactions" request (transactions/fetch-by-user request))
  (POST "/v1/users/:user-id/transactions" request (transactions/save-by-user request))
  (GET "/v1/users/:user-id/transactions/:transaction-id" request (transactions/find-by-user request))

  (route/not-found nil))

(def app
  (-> app-routes
    (json/wrap-json-body {:keywords? true})
    (json/wrap-json-response)))
