(ns balance.util
  "Balance Utilities")

(defn set-uuid
  "Configures an UUID into Map"
  [element] {:id (.toString (java.util.UUID/randomUUID))})
