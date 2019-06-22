(ns balance.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as json]))

(defroutes app-routes
  (GET "/" [] "")
  (route/not-found nil))

(def app
  (-> app-routes
    (json/wrap-json-body {:keywords? true})
    (json/wrap-json-response)))
