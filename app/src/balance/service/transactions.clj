;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [balance.util :refer :all]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;;; Show Transactions
(defn fetchByUser [user]
  (filter (fn [transaction] (= (:id user) (:userId transaction))) @transactions))

;;; Save Transactions
(defn saveByUser [user data] (identity data))
