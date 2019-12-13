(use 'clojure.test)
(require '[clojure.string :as str])


(defn validate [message key]
  "Returns TRUE if [key] is larger than 1 and i [message] consists only word characters and blank spaces; else return FALSE."
  (if (re-matches #"[\w ]+" message) 
    (> key 1) 
    false)
)

(defn prepare [message]
  "Returns [message] with underscore symbols '_' which replaced blank spaces."
  (str/replace message #" " "_")
)

(defn debug-encript [row length col index char]
  "Prints following DEBUG message: '2 * 6 + 2 = 14 [C]'"
  (println (str row " * " length " + " col " = " index " ["char"]"))
)

(defn get-fence-matrix [width height]
  (def row 0)
  (def direction-down false)        
  (vec
    (for [col (range 0 (count width))] (do
      (if (or (= 0 row) (= row (- height 1))) 
        (def direction-down (not direction-down)))
      (def matrix-index (+ (* row (count width)) col))
      ;;(debug-encript row (count width) col matrix-index (nth width col))
      (def row 
        (if (true? direction-down) 
          (inc row)
          (dec row)))
      matrix-index
    ))
  )
)

(defn encrypt [message key]
  (str/join
    (for [[pos char]
      (sort-by first 
        (zipmap 
          (get-fence-matrix message key)
          (vec message)))
    ] char)
  )
)

(defn encrypt-message [message key]
  (if (validate message key) 
    (encrypt (prepare message) key)  
    (str "ERROR: Invalid [message] or [key]!"))
)


;; ################################################
;; #                   TESTING                    #
;; ################################################

(testing "validate [message key]"
  (is (= false (validate "" 3)))
  (is (= false (validate "TEXT" 1)))
  (is (= false (validate "IT IS UN-VALID" 3)))
  (is (= true (validate "IT IS VALID" 3)))
  (is (= true (validate "abc 123" 2)))
)
(testing "prepare [message]"
  (is (= "IT_IS_OK" (prepare "IT IS OK")))
  (is (= "Long___space" (prepare "Long   space")))
  (is (= "WE_ARE_DISCOVERED_FLEE_AT_ONCE" (prepare "WE ARE DISCOVERED FLEE AT ONCE")))
)
(testing "encrypt [message 3]"
  (is (= "ABC" (encrypt "ABC" 3)))
  (is (= "ABDC" (encrypt "ABCD" 3)))
  (is (= "AEBDC" (encrypt "ABCDE" 3)))
  (is (= "AEBDFC" (encrypt "ABCDEF" 3)))
  (is (= "AEBDFCG" (encrypt "ABCDEFG" 3)))
  (is (= "AEBDFHCG" (encrypt "ABCDEFGH" 3)))
  (is (= "AEIBDFHCG" (encrypt "ABCDEFGHI" 3)))
  (is (= "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 3)))
)
(testing "encrypt [message 5]"
  (is (= "WIDTEDSE_A___CRF_OAEOELENERVEC" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 5)))
)
(testing "encrypt [message 10]"
  (is (= "WFE_L_DEAEERR_EEA_VTDO_EICOCSN" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 10)))
)
(testing "encrypt-message [message key]"
  (is (= "ERROR: Invalid [message] or [key]!" (encrypt-message "NOT-VALID" 3)))
  (is (= "ERROR: Invalid [message] or [key]!" (encrypt-message "TooSmallKey" 1)))
  (is (= "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" (encrypt-message "WE ARE DISCOVERED FLEE AT ONCE" 3)))
  (is (= "A____C_DB_" (encrypt-message "A B  C   D" 3)))
)


;; ################################################
;; #               NOT USED ANYMORE               #
;; ################################################

(defn make-2d-array [rows cols]
  "DEPRECATED: Returns 2d array filled with numbers which represent each cell index."
  (for [i (range 0 rows)]
    (for [j (range 0 cols)]
      (+ (* i cols) j))))
(testing "make-2d-array [rows cols]"
  (is (type clojure.lang.LazySeq) (type (make-2d-array 1 1)))
  (is (= 5 (nth (nth (make-2d-array 2 3) 1) 2))))

(defn print-2d-array [arr-2d]
  "DEPRECATED: Prints 2d array."
  (for [i (range 0 (count arr-2d))]
    (println (nth arr-2d i))))

(defn char-to-str-index [s char index]
  "DEPRECATED: Returns provided string [s] with replaced symbol [char] on [index]."
  (str (subs s 0 index) char (subs s (+ index 1)))
)

(defn make-empty-clone [s]
  "DEPRECATED: Returns empty string with a length of [s] string."
  (str/join (for [i (range 0 (count s))] "-"))
)

(defn get-2d-cell-str-index [cell key]
  "DEPRECATED: Returns 2d array's cell 'projection' to 1d row, which represents string, taking [key] as a 2d array's row length."
  (if (= 0 (mod cell key)) key (mod cell key))
)