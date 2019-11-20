(defproject clj-web-hands-on "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring "1.7.1"]]
  :ring {:handler clj-web-hands-on.core/handler}
  :plugins [[lein-ring "0.12.5"]])
