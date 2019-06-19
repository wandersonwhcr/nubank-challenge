(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :as response]
            [balance.util :refer :all]
            [balance.service.users :as users]))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))

  (GET "/v1/users" [] (response (users/fetch)))

  (POST "/v1/users" {:keys [body]}
    (let
      ;; Keep Identifier at First Position
      [data (merge {:id (generate-uuid)} body)]
      (response/header
        (response/created (users/save data))
        "X-Resource-Identifier"
        (get data :id))))

  (GET "/v1/users/:id" [id] (response (users/by id)))

  (DELETE "/v1/users/:id" [id]
    (response/status
      (response (users/delete id))
      204))

  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
