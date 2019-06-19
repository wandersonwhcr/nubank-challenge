(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [balance.util :refer :all]
            [balance.service.users :as users]))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (GET "/v1/users" [] (response (get-users)))
  (GET "/v1/users/:id" [id] (response (get-user id)))
  (POST "/v1/users" {:keys [body]}
    (let
      ;; Keep Identifier at First Position
      [data (merge {:id (generate-uuid)} body)]
      (response (save-user data))))
  (DELETE "/v1/users/:id" [id] (response (delete-user id)))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
