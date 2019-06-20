;;;; Transactions Service Layer
(ns balance.service.transactions
  (:require [json-schema.core :as json]))

;;; Transactions Bucket
(def ^:private transactions (atom {}))

;;; Transactions Schema
(def ^:private schema {:type "object"
                       :properties {:id {:type "string" :pattern "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$"}
                                    :type {:type "string" :enum ["in" "out"]}
                                    :value {:type "number" :multipleOf 0.01 :exclusiveMinimum 0}}
                       :additionalProperties false
                       :required [:id :type]})

;; Check a Transaction by Identifier
(defn ^:private has? [id]
  (when (not (contains? @transactions id))
    (throw (ex-info "Unknown Transaction" {:type :transaction-not-found, :id id}))))

;; Check a Transaction by User
(defn ^:private hasByUser? [user id]
  (when (has? id)
    (when (not (= (:id user) (:userId (get @transactions id)))))
      (throw (ex-info "Unknown Transaction" {:type :transaction-not-found, :id id}))))

;;; Fetch Transactions by User
(defn fetchByUser [user] (->> (vals @transactions)
  ; Only Transactions for User
  (filter (fn [transaction] (= (:id user) (:userId transaction))))
  ; Remove User Identifier from Element
  (map (fn [transaction] (dissoc transaction :userId)))))

;;; Save Transactions
(defn saveByUser [user data] (->> data
  ; Configure User Identifier
  (merge {:userId (:id user)})
  ; Store Transaction
  (swap! transactions assoc (:id data)))
  ; Saved Data
  (identity data))

;;; Show a Transaction by User by Identifier
(defn findByUser [user id] (do
  (hasByUser? user id)
  (dissoc (get @transactions id) :userId)))

;;; Delete a Transaction by User by Identifier
(defn deleteByUser [user id] (do
  (hasByUser? user id)
  (swap! transactions dissoc id)
  (identity nil)))
