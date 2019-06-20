(ns balance.http.action.balances
  (:refer-clojure :exclude [find])
  (:require
    [ring.util.response :refer :all]
    [balance.http.response :refer :all]))

(defn find [request]
  (try
    (response nil)
    (catch Exception e (internal-error))))
