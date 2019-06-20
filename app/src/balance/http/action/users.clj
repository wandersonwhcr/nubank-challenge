(ns balance.http.action.users
  (:require
    [balance.util :refer :all]
    [ring.util.response :refer :all]
    [balance.http.response :refer :all]
    [balance.service.users :as users-service]))

(defn fetch []
  (response (users-service/fetch)))

(defn save [data]
  (let
    [element (set-uuid data)]
    (try
      (do
        (users-service/save element)
        (saved element))
      (catch Exception e
        (case (:type (ex-data e))
          :user-invalid-data (unprocessable-entity (ex-data e))
          (internal-error))))))

(defn by [id]
  (try
    (response (users-service/by id))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        (internal-error)))))

(defn delete [id]
  (try
    (do
      (users-service/delete id)
      (no-content))
    (catch Exception e
      (case (:type (ex-data e))
        :user-not-found (not-found (ex-data e))
        (internal-error)))))
