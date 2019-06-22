(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [balance.http.controller.home :as home]
            [balance.http.controller.users :as users]))

(defroutes app-routes
  (GET "/" [] (home/index))

  (GET "/v1/users" [] (users/index))

  (route/not-found nil))

(def app
  (-> app-routes
    (json/wrap-json-body {:keywords? true})
    (json/wrap-json-response)))
