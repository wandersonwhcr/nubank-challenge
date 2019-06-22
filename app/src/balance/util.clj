(ns balance.util
  "Balance Utilities"
  (:refer-clojure :exclude [uuid?]))

(defn uuid
  "Generates a Random UUID"
  [] (.toString (java.util.UUID/randomUUID)))

(defn set-uuid
  "Configures an UUID into Map"
  [element] (merge {:id (uuid)} element))

(defn uuid?
  "Check if UUID is Correct"
  [value] (re-matches #"^[0-9a-f]{8}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{4}\-[0-9a-f]{12}$" value))
