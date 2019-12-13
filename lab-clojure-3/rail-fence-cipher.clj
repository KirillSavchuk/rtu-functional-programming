(use 'clojure.test)
(require '[clojure.string :as str])


;; ################################################
;; #                   COMMON                     #
;; ################################################

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

(defn debug-get-fence-matrix [row width col index]
  "Prints following DEBUG message: '2 * 6 + 2 = 14'"
  (println (str row " * " width " + " col " = " index))
)

(defn get-fence-matrix [width height]
  "Returns matrix cess indexes where Rail Fence Cipher should go."
  (def row 0)
  (def direction-down false)        
  (vec
    (for [col (range 0 width)] (do
      (if (or (= 0 row) (= row (- height 1))) 
        (def direction-down (not direction-down)))
      (def matrix-index (+ (* row width) col))
      ;; (debug-get-fence-matrix row width col matrix-index)
      (def row 
        (if (true? direction-down) 
          (inc row)
          (dec row)))
      matrix-index
    ))
  )
)

(defn get-2d-cell-str-index [cell key]
  "Returns 2d array's cell 'projection' to 1d row, which represents string, taking [key] as a 2d array's row length."
  (mod cell key)
)

(defn get-encrypt-fence-chars [message key]
  "Returns {key} matrix cell indexes and {value} message chars, where Rail Fence Cipher should go."
  (zipmap 
    (get-fence-matrix (count message) key)
    (vec message))
)

(defn get-decrypt-fence-chars [message key]
  (zipmap
    (sort (get-fence-matrix (count message) key))
    (vec message))
)


;; ################################################
;; #                   ENCRYPT                    #
;; ################################################

(defn encrypt [message key]
  (str/join
    (for [[pos char]
      (sort-by first (get-encrypt-fence-chars message key))
    ] char)
  )
)
(defn encrypt-message [message key]
  (if (validate message key) 
    (encrypt (prepare message) key)  
    (str "ERROR: Invalid [message] or [key]!"))
)


;; ################################################
;; #                   DECRYPT                    #
;; ################################################

(defn decrypt [message key]
  (str/join
    (for [[pos ch]
      (sort-by first
        (for [[index char] (get-decrypt-fence-chars message key)]
          [(get-2d-cell-str-index index (count message)) char]
        )
      )] 
      ch
    )
  )
)
(defn decrypt-message [message key]
  (if (validate message key) 
    (decrypt (prepare message) key)  
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
(testing "get-fence-matrix [width height]"
  (is (= [0 11 2 13 4 15 6 17 8 19] (get-fence-matrix 10 2)))
  (is (= [0 6 12 8 4] (get-fence-matrix 5 3)))
  (is (= [0 13 26 39 28 17 6 19 32 45 34 23] (get-fence-matrix 12 4)))
)
(testing "get-encrypt-fence-chars [message key]"
  (is (= {0 \A} (get-encrypt-fence-chars "A" 1)))
  (is (= {0 \A, 4 \B, 8 \C} (get-encrypt-fence-chars "ABC" 3)))
  (is (= {0 \A, 6 \B, 12 \C, 8 \D, 4 \E} (get-encrypt-fence-chars "ABCDE" 3)))
)
(testing "encrypt [message key]" 
  (is (= "ABC" (encrypt "ABC" 3)))
  (is (= "ABDC" (encrypt "ABCD" 3)))
  (is (= "AEBDC" (encrypt "ABCDE" 3)))
  (is (= "AEBDFC" (encrypt "ABCDEF" 3)))
  (is (= "AEBDFCG" (encrypt "ABCDEFG" 3)))
  (is (= "AEBDFHCG" (encrypt "ABCDEFGH" 3)))
  (is (= "AEIBDFHCG" (encrypt "ABCDEFGHI" 3)))
  (is (= "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 3)))
  (is (= "WIDTEDSE_A___CRF_OAEOELENERVEC" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 5)))
  (is (= "WFE_L_DEAEERR_EEA_VTDO_EICOCSN" (encrypt "WE_ARE_DISCOVERED_FLEE_AT_ONCE" 10)))
)
(testing "encrypt-message [message key]"
  (is (= "ERROR: Invalid [message] or [key]!" (encrypt-message "NOT-VALID" 3)))
  (is (= "ERROR: Invalid [message] or [key]!" (encrypt-message "TooSmallKey" 1)))
  (is (= "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" (encrypt-message "WE ARE DISCOVERED FLEE AT ONCE" 3)))
  (is (= "A____C_DB_" (encrypt-message "A B  C   D" 3)))
)
(testing "decrypt [message key]" 
  (is (= "ABC" (decrypt "ABC" 3)))
  (is (= "ABCD" (decrypt "ABDC" 3)))
  (is (= "ABCDE" (decrypt "AEBDC" 3)))
  (is (= "ABCDEF" (decrypt "AEBDFC" 3)))
  (is (= "ABCDEFG" (decrypt "AEBDFCG" 3)))
  (is (= "ABCDEFGH" (decrypt "AEBDFHCG" 3)))
  (is (= "ABCDEFGHI" (decrypt "AEIBDFHCG" 3)))
  (is (= "WE_ARE_DISCOVERED_FLEE_AT_ONCE" (decrypt "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" 3)))
  (is (= "WE_ARE_DISCOVERED_FLEE_AT_ONCE" (decrypt "WIDTEDSE_A___CRF_OAEOELENERVEC" 5)))
  (is (= "WE_ARE_DISCOVERED_FLEE_AT_ONCE" (decrypt "WFE_L_DEAEERR_EEA_VTDO_EICOCSN" 10)))
)
(testing "decrypt-message [message key]"
  (is (= "ERROR: Invalid [message] or [key]!" (decrypt-message "NOT-VALID" 3)))
  (is (= "ERROR: Invalid [message] or [key]!" (decrypt-message "TooSmallKey" 1)))
  (is (= "WE_ARE_DISCOVERED_FLEE_AT_ONCE" (decrypt-message "WRIVDETCEAEDSOEE_LEA_NE__CRF_O" 3)))
)
(testing "!--- Rail Fence Cipher ---!"
  (is (= "DECRYPT_ENCRYPT_NO_CHANGES" (decrypt-message (encrypt-message "DECRYPT_ENCRYPT_NO_CHANGES" 3) 3)))
  (is (= "ABC_123_xyz_0" (decrypt-message (encrypt-message "ABC_123_xyz_0" 5) 5)))
  (is (= "QWERTYUIOPASDFGHJKLZXCVBNM" (decrypt-message (encrypt-message "QWERTYUIOPASDFGHJKLZXCVBNM" 4) 4)))
  (is (= "QWERTYUIOPASDFGHJKLZXCVBNM" (decrypt-message (encrypt-message "QWERTYUIOPASDFGHJKLZXCVBNM" 10) 10)))
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