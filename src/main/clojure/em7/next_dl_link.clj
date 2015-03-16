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
  (loop [c coll
         prev-empty? false
         cur-vec []
         vecs []]
    (if (empty? c)
      (let [vs (conj vecs cur-vec)]
        vs)

      (if (string/blank? (first c))
        (recur (rest c)
               true
               (conj cur-vec (first c))
               vecs)
        
        (if prev-empty?
          (recur (rest c)
                 false
                 [(first c)]
                 (conj vecs cur-vec))
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

