(defproject poe-info "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.238"]

                 [clj-http "3.9.1"]
                 [org.clojure/data.json "0.2.6"]
                 [cheshire "5.8.1"]
                 [clojure.java-time "0.3.2"]
                 [org.clojure/core.match "0.3.0"]
                 [org.bovinegenius/exploding-fish "0.3.6"]
                 [spootnik/kinsky "0.1.23"]
                 [com.novemberain/monger "3.5.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [aleph "0.4.6"]
                 [manifold "0.1.8"]
                 [byte-streams "0.2.4"]
                 ]

  :source-paths ["src/clj/" "src/cljc" "src/cljs/"]
  :test-paths ["test/clj", "test/cljc"]

  :main ^:skip-aot poe-info.core
  :plugins [[lein-codox "0.10.5"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            ]

  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [cider/piggieback "0.3.1"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; for CIDER. Note that you should use the snapshot version, so it matches with the elpa dependency.
                   ;; :plugins [[cider/cider-nrepl "0.18.0-SNAPSHOT"]]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}}


  ;;:hooks [leiningen.cljsbuild]
  :cljsbuild {:repl-listen-port 9000
              :repl-launch-commands
              ;; Launch command for connecting the page of choice to the REPL.
              ;; Only works if the page at URL automatically connects to the REPL,
              ;; like http://localhost:3000/repl-demo does.
              ;;     $ lein trampoline cljsbuild repl-launch firefox <URL>
              {"firefox" ["firefox"
                          :stdout ".repl-firefox-out"
                          :stderr ".repl-firefox-err"]
               ;; Launch command for interacting with your ClojureScript at a REPL,
               ;; without browsing to the app (a static HTML file is used).
               ;;     $ lein trampoline cljsbuild repl-launch firefox-naked
               "firefox-naked" ["firefox"
                                "resources/private/html/naked.html"
                                :stdout ".repl-firefox-naked-out"
                                :stderr ".repl-firefox-naked-err"]}
              #_:test-commands
              ;; Test command for running the unit tests in "test-cljs" (see below).
              ;;     $ lein cljsbuild test
              #_{"unit" ["firefox"
                       "resources/private/html/unit-test.html"]}
              :builds
              [{:id "dev"
                :source-paths ["src"]

                :compiler {:main poe-info.app
                           :asset-path "js/compiled/out"
                           :output-to "target/js/compiled/poe_info.js"
                           :source-map true
                           :output-dir "target/js/compiled/out"
                           :source-map-timestamp true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               ;; This next build is a compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/poe_info.js"
                           :main poe-info.app
                           :optimizations :advanced
                           :pretty-print false}}
               {:id "test"
                :source-paths ["src/cljs" "test/cljs" "test/cljc"]
                :compiler {:output-to "target/js/unit-test.js"
                           :output-dir "target/js/"
                           :optimizations :whitespace
                           :source-map "target/js/unit-test.map"
                           :pretty-print true}}

               ]}

  )
