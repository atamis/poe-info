(defproject poe-info "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.9.1"]
                 [org.clojure/data.json "0.2.6"]
                 [cheshire "5.8.1"]
                 [clojure.java-time "0.3.2"]
                 [org.clojure/core.match "0.3.0"]
                 [org.bovinegenius/exploding-fish "0.3.6"]
                 [spootnik/kinsky "0.1.23"]
                 ]
  :main ^:skip-aot poe-info.core
  :plugins [[lein-codox "0.10.5"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
