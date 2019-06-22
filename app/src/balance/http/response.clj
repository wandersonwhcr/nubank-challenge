(ns balance.http.response
  "Balance HTTP Response"
  (:require [ring.util.response :refer :all]))

(defn no-content
  "HTTP 204 No Content"
  [] (status nil 204))
