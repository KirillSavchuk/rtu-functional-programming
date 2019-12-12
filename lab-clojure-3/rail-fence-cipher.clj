(use 'clojure.test)
(require '[clojure.string :as str])

(defn validate [message]
  (re-matches #"[\w ]+" message))

(defn prepare [message]
  (str/replace message #" " "_"))

(defn make-2d-array [rows cols]
  (for [i (range 0 rows)]
    (for [j (range 0 cols)]
      (+ (* i cols) j))))

(defn print-2d-array [arr-2d]
  (for [i (range 0 (count arr-2d))]
    (println (nth arr-2d i))))

(defn encrypt [message key]
  (if (validate message) 
    (do
      (def text (prepare message))
      (def arr (make-2d-array key (count text)))
      (print-2d-array arr))
    (str "ERROR! Message doesn't match RegEx: [\\w ]+")))

(encrypt "WEAREDIS" 3)


;; ################################################
;; #                   TESTING                    #
;; ################################################

(testing "validate [message]"
  (is (= "IT IS VALID" (validate "IT IS VALID")))
  (is (= "100 is number" (validate "100 is number")))
  (is (= nil (validate "IT IS UN-VALID")))
  (is (= nil (validate "Will it work?"))))

(testing "prepare [message]"
  (is (= "IT_IS_OK" (prepare "IT IS OK")))
  (is (= "Long___space" (prepare "Long   space"))))

(testing "make-2d-array [rows cols]"
  (is (type clojure.lang.LazySeq) (type (make-2d-array 1 1)))
  (is (= 5 (nth (nth (make-2d-array 2 3) 1) 2))))