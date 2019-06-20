;;;; Balance Util
(ns balance.util)

;;; Generate an UUID String
(defn generate-uuid [] (.toString (java.util.UUID/randomUUID)))

;;; Configure an UUID on Element
(defn set-uuid [data] (merge {:id (generate-uuid)} data))

;;; Coalesce
(defn coalesce [& args]
  ;; Return First Element From
  (first
    ;; Convert `args` to non-nil List Elements
    (keep identity args)))
