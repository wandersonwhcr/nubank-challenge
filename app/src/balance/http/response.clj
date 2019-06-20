(ns balance.http.response
  (:require [ring.util.response :refer [response, status, header, created]]))

;;; Response as Resource Created
(defn saved [resource]
  (header (status nil 204) "X-Resource-Identifier" (:id resource)))
;;; Response as No Content
(defn no-content [] (status nil 204))
;;; Response as Unprocessable Entity
(defn unprocessable-entity [content] (status (response content) 422))
;;; Response as Internal Error
(defn internal-error [] (status nil 500))
