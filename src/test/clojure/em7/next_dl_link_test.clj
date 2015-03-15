(ns em7.next-dl-link-test
  (:require [clojure.test :refer :all]
            [em7.next-dl-link :refer :all]))

(deftest partition-seq-by-empty-lines-test
  (let [data (list "AAA" "AAB" "AAC" "" "" "BAA" "BAB" "BAC" "BAD" "" "CAA" "CAB" "CAC" "")
        spl  (partition-seq-by-empty-lines data)]  
    (is (= (count spl) 3))
    (is (= (count (first spl)) 5))
    (is (= (count (second spl)) 5))
    (is (= (count (second (rest spl))) 4))))

(deftest find-links-to-open-test
  (let [data [["A" "B" "C" "" ""]
              ["A" ""]
              ["l1" "l2" "l3" "l4" "" ""]
              ["s1" "s2" "s3" ""]]
        links (find-links-to-open data)]
    (is (= (count links) 6))
    (is (= (first links) "l1"))
    (is (= (second links) "l2"))))

(deftest find-backup-links-test
  (let [data [["A" "B" "C" "" ""]
              ["A" ""]
              ["l1" "l2" "l3" "l4" "" ""]
              ["s1" "s2" "s3" ""]]
        backs (find-backup-links data)]
    (is (= (count backs) 4))
    (is (= (first backs) "s1"))
    (is (= (second backs) "s2"))))
