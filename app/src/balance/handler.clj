(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer :all]
            [balance.http.action.users :as users-action]))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))

  (GET "/v1/users" [] (users-action/fetch))
  (POST "/v1/users" {:keys [body]} (users-action/save body))
  (GET "/v1/users/:id" [id] (users-action/by id))
  (DELETE "/v1/users/:id" [id] (users-action/delete id))

  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
