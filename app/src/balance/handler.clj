(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer :all]))

(defn get-users [] [
  {:id "abc123" :name "John Doe"}
  {:id "xyz456" :name "João da Silva"}])

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (GET "/v1/users" [] (response (get-users)))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  json/wrap-json-body
  json/wrap-json-response))
