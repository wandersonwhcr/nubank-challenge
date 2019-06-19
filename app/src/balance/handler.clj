(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer :all]
            [balance.util :refer :all]
            [balance.service.users :as users]))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))

  (GET "/v1/users" [] (response (users/fetch)))

  (POST "/v1/users" {:keys [body]}
    (let
      [data (set-uuid body)]
      (try
        (created (users/save data))
        (catch Exception e
          (case (:type (ex-data e))
            :user-invalid-data (unprocessable-entity (ex-data e)))))))

  (GET "/v1/users/:id" [id]
    (try
      (response (users/by id))
      (catch Exception e
        (case (get (ex-data e) :type)
          :user-not-found (not-found (ex-data e))
          (status {} 500)))))

  (DELETE "/v1/users/:id" [id]
    (try
      (status (response (users/delete id)) 204)
      (catch Exception e
        (case (get (ex-data e) :type)
          :user-not-found (not-found (ex-data e))
          (status {} 500)))))

  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
