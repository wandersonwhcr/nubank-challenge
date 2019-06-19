;;;; Balance Util
(ns balance.util)

;;; Generate an UUID String
(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))
