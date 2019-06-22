(ns balance.util
  "Balance Utilities")

(defn uuid
  "Generates a Random UUID"
  [] (.toString (java.util.UUID/randomUUID)))

(defn set-uuid
  "Configures an UUID into Map"
  [element] (merge {:id (.toString (java.util.UUID/randomUUID))} element))
