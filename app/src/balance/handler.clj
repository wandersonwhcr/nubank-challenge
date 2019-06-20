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
        (do (users/save data) (created-h "/v1/users" data))
        (catch Exception e
          (case (:type (ex-data e))
            :user-invalid-data (unprocessable-entity (ex-data e))
            (internal-error))))))

  (GET "/v1/users/:id" [id]
    (try
      (response (users/by id))
      (catch Exception e
        (case (:type (ex-data e))
          :user-not-found (not-found (ex-data e))
          (internal-error)))))

  (DELETE "/v1/users/:id" [id]
    (try
      (do (users/delete id) (no-content))
      (catch Exception e
        (case (:type (ex-data e))
          :user-not-found (not-found (ex-data e))
          (internal-error)))))

  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
