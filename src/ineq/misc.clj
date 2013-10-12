(ns ineq.misc)

(def ^:dynamic *verbose* 'false)

(defn log-val [tag & val]
  (if-not ineq.misc/*verbose* nil
          (println (.toString (java.util.Date.)) tag val)))

(defn abs [x]
  (if (> 0 x) (- x)
      x))

