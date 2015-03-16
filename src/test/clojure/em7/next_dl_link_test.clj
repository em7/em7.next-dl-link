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

(deftest get-server-name-test
  (let [data ["http://www.server1.com/link/rel"
              "https://server2.org/link/something"
              "www.server1.com/blahblahblah"]
        names (map get-server-name data)]
    (is (= (count names) 3))
    (let [[name1 name2 name3] names]
      (is (= name1 "www.server1.com")
          (= name2 "server2.org"))
      (is (= name2 "server2.org"))
      (is (= name3 "www.server1.com"))
      (is (= name1 name3)))))

(deftest split-first-rest-value-test
  (let [data {"server1" ["server1/link1" "server1/link2" "server1/link3"]
              "server2" ["server2/link1"]
              "server3" ["server3/link1" "server3/link2"]}
        splitted (split-first-rest-value data)]
    (is (= (count splitted) 3))
    (is (seq? splitted))
    (let [[srv1 srv2 srv3] splitted]
      (is (= (count srv1) 2))
      (is (= (count srv2) 2))
      (is (= (count srv3) 2))
      (let [[f1 r1] srv1
            [f2 r2] srv2
            [f3 r3] srv3]
        (is (= f1 "server3/link1"))
        (is (= (count r1) 1))
        (is (= (first r1) "server3/link2"))

        (is (= f2 "server1/link1"))
        (is (= (count r2) 2))
        (is (= (first r2) "server1/link2"))
        (is (= (second r2) "server1/link3"))

        (is (= f3 "server2/link1"))
        (is (= (count r3) 0))))))

(deftest get-links-to-open-test
  (let [data ["http://www.server1.com/link/rel"
              "www.server1.com/blahblahblah"
              "https://server2.org/link/something"             
              "http://www.server1.com/link/not"
              "server2.org/link/not"
              ""
              ""]
        [to-open to-save] (get-links-to-open data)]
    (is (= (count to-open) 2))
    (is (= (first to-open) "http://www.server1.com/link/rel"))
    (is (= (second to-open) "https://server2.org/link/something"))
    (is (= (count to-save) 5))
    (is (= (first to-save) "www.server1.com/blahblahblah"))
    (is (= (second to-save) "http://www.server1.com/link/not"))
    (is (= (nth to-save 2) "server2.org/link/not"))
    (is (= (nth to-save 3) ""))
    (is (= (nth to-save 4) ""))))
