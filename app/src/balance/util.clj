(ns balance.util
  "Balance Utilities")

(defn set-uuid
  "Configures an UUID into Map"
  [element] (merge {:id (.toString (java.util.UUID/randomUUID))} element))
