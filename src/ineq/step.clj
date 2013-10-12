(ns ineq.step
  ;; (:use [incanter core stats charts])
  (:require [ineq.misc])
  )

(def N-COINS 1000)
(def COIN-TRY 10)
(def EXP-TRY 10)

(defn flip []
  (rand-int 2))

(defn do-coin []
  (let [bits (for [n (range COIN-TRY)] (flip))
        {ones true, zeroes false} (group-by #(= 1 %) bits)
        n0 (count zeroes)
        n1 (count ones)]
    n0))

(defn do-coins []
  (let [
        n-zeroes (vec (for [n (range N-COINS)] (do-coin)))
        c1 (get n-zeroes 0)
        c-rand (get n-zeroes (rand-int N-COINS))
        [_nmin c-min] (apply min-key second (map-indexed vector n-zeroes))
        v1 (/ c1 COIN-TRY)
        v-rand (/ c-rand COIN-TRY)
        v-min (/ c-min COIN-TRY)]
    {:1 v1
     :rand v-rand
     :min v-min}))

(defn exp [n]
  (let [
        tries (for [n (range n)] (do-coins))
        sum1 (reduce + (map :1 tries))
        sum-rand (reduce + (map :rand tries))
        sum-min (reduce + (map :min tries))
        avg1 (float (/ sum1 n))
        avg-rand (float (/ sum-rand n))
        avg-min (float (/ sum-min n))]
    {:1 avg1
     :rand avg-rand
     :min avg-min}))

(defn calc [n]
  (ineq.misc/log-val "n" n)
  (exp n))

