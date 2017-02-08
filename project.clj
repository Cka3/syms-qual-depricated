(defproject hello-electron "0.1.0-SNAPSHOT"
  :source-paths ["src"]
  :description "A hello world application for electron"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [figwheel "0.5.8"]
                 [reagent "0.6.0"]
                 [ring/ring-core "1.5.0"]
                 [binaryage/devtools "0.8.2"]
                 [figwheel-sidecar "0.5.8"]
                 [com.cemerick/piggieback "0.2.1"]]

  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.8"]
            [cider/cider-nrepl "0.12.0"]]

  :clean-targets ^{:protect false} ["resources/main.js"
                                    "resources/public/js/ui-core.js"
                                    "resources/public/js/ui-core.js.map"
                                    "resources/public/js/ui-out"]
  :cljsbuild
  {:builds
   [{:source-paths ["electron_src"]
     :id "electron-dev"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-dev"
                :optimizations :simple
                :pretty-print true
                :cache-analysis true}}
    {:source-paths ["ui_src" "dev_src"]
     :id "dev"
     :compiler {:output-to "resources/public/js/ui-core.js"
                :output-dir "resources/public/js/ui-out"
                :source-map true
                :asset-path "js/ui-out"
                :optimizations :none
                :cache-analysis true
                :main "dev.core"
                :preloads [devtools.preload]}}
    {:source-paths ["electron_src"]
     :id "electron-release"
     :compiler {:output-to "resources/main.js"
                :output-dir "resources/public/js/electron-release"
                :optimizations :simple
                :pretty-print true
                :cache-analysis true}}
    {:source-paths ["ui_src"]
     :id "frontend-release"
     :compiler {:output-to "resources/public/js/ui-core.js"
                :output-dir "resources/public/js/ui-release-out"
                :source-map "resources/public/js/ui-core.js.map"
                :optimizations :simple
                :cache-analysis true
                :main "ui.core"}}]}
  :figwheel {:http-server-root "public"
             :css-dirs ["resources/public/css"]
             :ring-handler tools.figwheel-middleware/app
             :server-port 3449
             :nrepl-port 7888}

  :profiles {:dev
             {:dependencies [[binaryage/devtools "0.8.2"]
                             [figwheel-sidecar "0.5.8"]
                             [com.cemerick/piggieback "0.2.1"]]
              ;; need to add dev source path here to get user.clj loaded
              :source-paths ["ui_src" "dev_src"]
              ;; for CIDER

              :plugins [[cider/cider-nrepl "0.12.0"]]
              :repl-options {; for nREPL dev you really need to limit output
                             :init (set! *print-length* 50)
                             :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
