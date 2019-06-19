(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer :all]))

(def users {
  "abc123" {:id "abc123" :name "John Doe"}
  "xyz456" {:id "xyz456" :name "João da Silva"}})

(defn get-users [] (vals users))

(defn get-user [id] (get users id))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (GET "/v1/users" [] (response (get-users)))
  (GET "/v1/users/:id" [id] (response (get-user id)))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  json/wrap-json-body
  json/wrap-json-response))
