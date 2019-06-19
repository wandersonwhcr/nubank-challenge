(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]))

(def users {})

(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))

(defn get-users [] (vals users))

(defn get-user [id] (get users id))

(defn save-user [data] (do
  (def user
    (merge {:id (generate-uuid)} data))
  (def users (assoc users (get user :id) user))
  nil))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (GET "/v1/users" [] (response (get-users)))
  (GET "/v1/users/:id" [id] (response (get-user id)))
  (POST "/v1/users" {:keys [body]} (response (save-user body)))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  (json/wrap-json-body {:keywords? true})
  json/wrap-json-response))
