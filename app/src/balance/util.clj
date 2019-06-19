;;;; Balance Util
(ns balance.util
  (:require [ring.util.response :refer [response, status]]))

;;; Generate an UUID String
(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))

;;; Configure an UUID on Element
(defn set-uuid [data] (merge {:id (generate-uuid)} data))

;;; Response with Unprocessable Entity
(defn unprocessable-entity [content] (status (response content) 422))
