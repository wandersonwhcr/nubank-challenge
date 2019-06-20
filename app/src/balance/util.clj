;;;; Balance Util
(ns balance.util
  (:require [ring.util.response :refer [response, status, header, created]]))

;;; Generate an UUID String
(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))

;;; Configure an UUID on Element
(defn set-uuid [data] (merge {:id (generate-uuid)} data))

;;; Response as Resource Created
(defn created-h [base-urn resource]
  (header (created (str base-urn "/" (:id resource))) "X-Resource-Identifier" (:id resource)))
;;; Response as No Content
(defn no-content [] (status nil 204))
;;; Response as Unprocessable Entity
(defn unprocessable-entity [content] (status (response content) 422))
;;; Response as Internal Error
(defn internal-error [] (status nil 500))
