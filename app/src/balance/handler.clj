(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))

(def users {})
(defn get-users [] (vals users))
(defn get-user [id] (get users id))
(defn save-user [data] (do
  (def users (assoc users (get data :id) data))
  nil))
(defn delete-user [id] (do
  (def users (dissoc users id))
  nil))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (GET "/v1/users" [] (response (get-users)))
  (GET "/v1/users/:id" [id] (response (get-user id)))
  (POST "/v1/users" {:keys [body]}
    (let
      [data (merge {:id (generate-uuid)} body)]
      (response (save-user data))))
  (DELETE "/v1/users/:id" [id] (response (delete-user id)))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
