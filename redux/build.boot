(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.1" :scope "test"]])

(require '[boot.task-helpers]
         '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.5.2")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  push {:ensure-clean false}
  pom  {:project     'cljsjs/redux
        :version     +version+
        :description "Predictable state container for JavaScript apps"
        :url         "https://github.com/reactjs/redux"
        :license     {"MIT" "https://opensource.org/licenses/MIT"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
    (download :url "https://cdnjs.cloudflare.com/ajax/libs/redux/3.5.2/redux.js")
    (download :url "https://cdnjs.cloudflare.com/ajax/libs/redux/3.5.2/redux.min.js")
    (sift :move {#"^redux.js$" "cljsjs/redux/development/redux.inc.js"
                 #"^redux.min.js$" "cljsjs/redux/production/redux.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.redux")
    (pom)
          (jar)))
