(ns clj-web-hands-on.core
  (:use ring.adapter.jetty)
  (:use clj-web-hands-on.task0)
  (:require [clojure.string :as str]))

(defn query-to-map [lol]
	(->> (str/split lol #"&") 
	     (map #(str/split % #"=")) 
	     (map (fn [[k v]] [(keyword k) v])) 
	     (into {}))
	)

(defn query-to-html-tag-list [query]
  (for [[k v] (query-to-map query)]
    (str
      (if (= (name k) v) 
        (str "<" (name k) "><b>" v "</b></" (name k) ">")
        (str "<" (name k) ">" v "</" (name k) ">")
      )
    )
  )
)

(defn list-to-html-string [list, sepparator]
  (str/join sepparator list)
)


(defn query-to-html-option-list [query]
  (for [[k v] (query-to-map query)]
    (str "<option value='" (name k) "'>" v "</option>")
  )
)

(defn option-list-to-whole-select [list]
  (str "<select>" (list-to-html-string list " ") "</select>")
)


(comment " 
>>> Takes Query parametrs and return Map
[Input URL]: http://localhost:3002/?Kirill=Savchuk&a=1&true=true&bb=22&ccc=333
[Output]: 
{:Kirill "Savchuk", :a "1", :true "true", :bb "22", :ccc "333"}

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str (query-to-map (request :query-string)))})
")


(comment " 
>>> Takes Query parametrs and return HTML as Tag/Value pair
[Input URL]: http://localhost:3002/?Kirill=Savchuk&a=1&true=true&bb=22&ccc=333
[Output]: 
<kirill>Savchuk</kirill>
<a>1</a>
<true><b>true</b></true>
<bb>22</bb> 
<ccc>333</ccc>

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (list-to-html-string 
           (query-to-html-tag-list (request :query-string)) 
           ""
         )})
")

(comment " 
>>> Takes Query parametrs and return <select> tag with <option> list
[Input URL]: http://localhost:3002/?volvo=Volvo&saab=Saab&mercedes=Mercedes&audi=Audi
[Output]: 
<select>
<option value="volvo">Volvo</option> 
<option value="saab">Saab</option> 
<option value="mercedes">Mercedes</option> 
<option value="audi">Audi</option>
</select>

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (option-list-to-whole-select
           (query-to-html-option-list (request :query-string))
         )})
")

; Main task:
(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (option-list-to-whole-select
           (query-to-html-option-list (request :query-string))
         )})

