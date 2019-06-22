(ns balance.http.controller.home
  "Balance HTTP Controller for Home"
  (:require [ring.util.response :refer :all]))

(defn index
  "Index Action"
  [] (response {:name "balance" :version "1.0.0-alpha"}))
