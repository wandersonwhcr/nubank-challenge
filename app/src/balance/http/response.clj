(ns balance.http.response
  "Balance HTTP Response"
  (:require [ring.util.response :refer :all]))

(defn no-content
  "HTTP 204 No Content"
  [] (status (response nil) 204))

(defn unprocessable-entity
  "HTTP 422 Unprocessable Entity"
  ([] (unprocessable-entity nil))
  ([body] (status (response body) 422)))

(defn internal-error
  "HTTP 500 Internal Server Error"
  [] (status (response nil) 500))
