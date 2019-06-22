(defproject balance "1.0.0-alpha"
  :description "Balance Clojure Application"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [luposlip/json-schema "0.1.6"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler balance.handler/app :open-browser? false}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
