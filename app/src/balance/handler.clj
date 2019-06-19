(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer :all]))

(defroutes app-routes
  (GET "/" [] (response "Hello World"))
  (route/not-found "Not Found"))

(def app (-> app-routes))
