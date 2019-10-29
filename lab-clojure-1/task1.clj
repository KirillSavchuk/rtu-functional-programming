;; Clojure docs:
;; https://kimh.github.io/clojure-by-example/#about
;; https://clojure.org/reference/data_structures#Maps
;; https://clojure.org/api/cheatsheet

(require '[clojure.string :as str])
(use 'clojure.test)

(defn average
  [numbers]
    (if (empty? numbers)
      0.0
      (float  (/ (reduce + numbers) (count numbers)))))

(defn get-special-students-average-makr [students]
  (average
    (for [
      [x y] 
      (filter 
        (fn [[k v]] (str/ends-with? k "a")) students
      )
    ] y)
  )
)

(print "TEST: average\n")
(is (= 0.0 (average [])))
(is (= 5.0 (average [5])))
(is (= 3.5 (average [1 2 3 4 5 6])))

(print "TEST: get-special-students-average-makr\n")
(is (= 0.0 (average {})))
(is (= 10.0 (get-special-students-average-makr {"Anja" 10})))
(is (= 10.0 (get-special-students-average-makr {"Anja" 10 "Lauris" 5})))
(is (= 9.5 (get-special-students-average-makr {"Anja" 10 "Lauris" 5 "Sandra" 9})))
(is (= 7.25 (get-special-students-average-makr {"Vasja" 8 "Petja" 4 "Natasha" 7 "Anja" 10})))

(def task-students {"Inese" 10 "Vasja" 8 "Petja" 4 "Natasha" 7 "Anja" 10 "Lauris" 6 "Sandra" 8 "Krišjanis" 9} )
(is (= 7.4 (get-special-students-average-makr task-students)))
;; expected: (= 7.4 (get-special-students-average-makr task-students))
;;   actual: (not (= 7.4 7.4))
;; >>> WHY? Can you please explain