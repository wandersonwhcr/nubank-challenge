;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [balance.util :refer :all]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;;; Show Transactions
(defn fetchByUser [user]
  (filter (fn [transaction] (= (:id user) (:userId transaction))) (vals @transactions)))

;;; Save Transactions
(defn saveByUser [user data]
  (swap! transactions assoc (:id data) (merge data {:userId (:id user)})))
