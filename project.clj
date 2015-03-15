(defproject em7.next-dl-link "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths      ["src/main/clojure"]
  :test-paths        ["src/test/clojure"]
  :resource-paths    ["src/main/resources"]
  :java-source-paths ["src/main/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"]
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot em7.next-dl-link
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
