(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer :all]))

(defroutes app-routes
  (GET "/" [] (response {:message "Hello World"}))
  (route/not-found (response {:message "Not Found"})))

(def app (->
  app-routes
  json/wrap-json-body
  json/wrap-json-response))
