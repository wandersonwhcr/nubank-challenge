;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [balance.util :refer :all]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;;; Show Transactions
(defn fetch [] (coalesce (vals @transactions) []))
