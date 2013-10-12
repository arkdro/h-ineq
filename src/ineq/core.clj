(ns ineq.core
  {:doc "hoeffding inequality"}
  (:use [clojure.tools.cli :only [cli]])
  (:require clojure.string)
  (:use clojure.tools.trace)
  (:require ineq.misc)
  (:require ineq.step)
  (:gen-class)
  )

(def EXP-TRY 100000)

;; (trace-ns 'ineq.step)
;; (trace-vars ineq.step/calc-one-step)
;; (trace-vars ineq.step/ineq)
;; (trace-vars ineq.step/ineq-aux)
;; (trace-vars ineq.step/get-misclassified)
;; (trace-vars ineq.step/update-w)
;; (trace-vars ineq.step/is-misclassified)
;; (untrace-vars ineq.step/calc-diff-prob)
;; (untrace-vars ineq.step/calc-diff-prob-aux)

(defn call-calc [n verbose]
  (if verbose
    (binding [*out* *err* ineq.misc/*verbose* 'true]
      (time (ineq.step/calc n)))
    (ineq.step/calc n)))

(defn print-result [res]
  (println "res: " res))

(defn -main [& args]
  (let [opts (cli
              args
              ["-v" "--[no-]verbose" :default false]
              ["-n" "--n" "N of experiments"
               :parse-fn #(Integer. %)
               :default EXP-TRY]
              )
        [options _ _] opts
        res (call-calc (:n options)
                       (:verbose options)
                       )
        ]
    (print-result res)))

