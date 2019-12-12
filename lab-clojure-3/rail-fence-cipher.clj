(use 'clojure.test)
(require '[clojure.string :as str])

(defn make-2d-array [rows cols]
  "DEPRECATED: Returns 2d array filled with numbers which represent each cell index."
  (for [i (range 0 rows)]
    (for [j (range 0 cols)]
      (+ (* i cols) j))))

(defn print-2d-array [arr-2d]
  "DEPRECATED: Prints 2d array."
  (for [i (range 0 (count arr-2d))]
    (println (nth arr-2d i))))

(defn validate [message]
  "Returns [message] parameter if only word characters and blank spaces are used. Else return [nil]+"
  (re-matches #"[\w ]+" message)
)

(defn prepare [message]
  "Returns [message] with underscore symbols '_' which replaced blank spaces."
  (str/replace message #" " "_")
)

(defn char-to-str-index [s char index]
  "Returns provided string [s] with replaced symbol [char] on [index]."
  (str (subs s 0 index) char (subs s (+ index 1)))
)

(defn make-empty-clone [s]
  "Retruns empty string with a length of [s] string."
  (str/join (for [i (range 0 (count s))] "-"))
)

(defn get-2d-cell-str-index [cell key]
  "Returns 2d array's cell 'projection' to 1d row, which represents string, taking [key] as a 2d array's row length."
  (if (= 0 (mod cell key)) key (mod cell key))
)

;; WEAREDISCOVEREDFLEEATONCE
(defn encrypt [message key]
  (str/join
    (for [
      [pos char]
      (sort-by first
        (for [col (range 0 (count message))] 
          (do
            (if (or (= 0 row) (= row (- key 1)))
              (def direction-down (not direction-down)))
            
            (def matrix-index (+ (* row (count message) col)))

            (def row
              (if (true? direction-down)
                (inc row)
                (dec row)))

            [matrix-index (nth message col)]
          )
        )
      )
    ] char)
  )
)

(testing "encrypt [rows cols]"
  (is "ABC" (encrypt "ABC" 3))
  (is "ABDC" (encrypt "ABCD" 3))
  (is "AEBDC" (encrypt "ABCDE" 3))
  (is "AEBDFC" (encrypt "ABCDEF" 3))
  (is "AEBDFCG" (encrypt "ABCDEFG" 3))
  (is "AEBDFHCG" (encrypt "ABCDEFGH" 3))
  (is "AEIBDFHCG" (encrypt "ABCDEFGHI" 3))
  (is "WECRLTEERDSOEEFEAOCAIVDEN" (encrypt "WEAREDISCOVEREDFLEEATONCE" 3))
)
(encrypt "WEAREDISCOVEREDFLEEATONCE" 3)

;; (defn encrypt [message key]
;;   (if (validate message) 
;;     (do
;;       (def text (prepare message))
;;       (def arr (make-2d-array key (count text)))
;;       (def direction-down false)
;;       (def row 0)
;;       (print-2d-array arr)
;;       (for [col (range 0 (count text))]
;;         (do 
;;           (if (or (= 0 row) (= row (- key 1)))
;;             (def direction-down (not direction-down)))
;;           (def row 
;;             (if (true? direction-down)
;;               (inc row)
;;               (dec row)))
;;         )
;;       )
;;     )
;;     (str "ERROR! Message doesn't match RegEx: [\\w ]+"))
;; )


(defn go [message key]
  (def row 0)
  (for [col (range 0 (count message) )]
    (do 
      (if (or (= 0 row) (= row (- key 1)))
        (def direction-down (not direction-down)))

      (nth message (+ (* row key) col))
      ;; (def row 
      ;;   (if (true? direction-down)
      ;;     (inc row)
      ;;     (dec row))) (nth message (+ (* row key) col))
    )
  )
)

;; (print (go "ABCDEF" 3))


;; ################################################
;; #                   TESTING                    #
;; ################################################

;; (testing "validate [message]"
;;   (is (= "IT IS VALID" (validate "IT IS VALID")))
;;   (is (= "100 is number" (validate "100 is number")))
;;   (is (= nil (validate "IT IS UN-VALID")))
;;   (is (= nil (validate "Will it work?"))))

;; (testing "prepare [message]"
;;   (is (= "IT_IS_OK" (prepare "IT IS OK")))
;;   (is (= "Long___space" (prepare "Long   space"))))

;; (testing "make-2d-array [rows cols]"
;;   (is (type clojure.lang.LazySeq) (type (make-2d-array 1 1)))
;;   (is (= 5 (nth (nth (make-2d-array 2 3) 1) 2))))