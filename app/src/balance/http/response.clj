(ns balance.http.response
  "Balance HTTP Response"
  (:require [ring.util.response :refer :all]))

(defn no-content
  "HTTP 204 No Content"
  [] (status nil 204))

(defn unprocessable-entity
  "HTTP 422 Unprocessable Entity"
  [info] (status nil 422))
