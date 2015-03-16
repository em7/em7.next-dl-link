(ns em7.next-dl-link
  (:require [clojure.string :as string])
  (:gen-class))

; the file has following fomat:
; some stuff
; <empty lines>
; ...
; links to open
; <empty lines>
; stored links
; possible <empty lines>
;
; the links to open (for distinct serves) should be opened,
; then the links should be copeid to stored links and the file saved
; the empty lines and rest of the file should be retained

(def input-file "dl.txt")

(defn read-all-input-lines
  "Reads all lines from the file."
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (doall (line-seq rdr))))

(defn partition-seq-by-empty-lines
  "Partitions the input sequence of strings by empty strings,
   resulting in multiple seqs of strings."
  [coll]

  ; FIXME this function is a bit ugly. I even needed to put comments
  ; here so my brain wouldn't blow when I see it again after a year.
  ; Maybe there should be a better functional way than just a loop.
  
  (loop [c coll             ; the collection of all lines
         prev-empty? false  ; was the previous line empty?
         cur-vec []         ; the vector wit current part
         vecs []]           ; vector with all parts <Vec of Vectors>
    ; if the end is reached, just add the current vector to the end of
    ; vecs and return
    (if (empty? c)
      (let [vs (conj vecs cur-vec)]
        vs)

    ; if current line is empty, set the empty flag, add it to current
    ; partition and continue
      (if (string/blank? (first c))
        (recur (rest c)
               true
               (conj cur-vec (first c))
               vecs)

    ; previous was empty, current is non-empty. Set the previous emtpy flag
    ; to false, create a new partition and put previous partition to vecs
        (if prev-empty?
          (recur (rest c)
                 false
                 [(first c)]
                 (conj vecs cur-vec))
          
    ; previous was not empty, current is non-empty. Normal case, add current
    ; line to the current partition and continue
          (recur (rest c)
                 false
                 (conj cur-vec (first c))
                 vecs))))))

(defn find-links-to-open
  "Gets the seq of links to be opened. It should be the
   last but one seq in input."
  [colls]
  (let [n (- (count colls) 2)]
    (nth colls n)))

(defn find-backup-links
  "Gets the seq of backups of links. It should be the last
   seq in input."
  [colls]
  (let [n (- (count colls) 1)]
    (nth colls n)))

(defn get-server-name
  "Gets the name of server from the http[s] link. If could not be found,
   returns empty string."
  [link]
  (let [pat #"(https?://){0,1}([^/]+)/?"
        [_ _ name] (re-find pat link)]
    (if (nil? name)
      ""
      name)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

