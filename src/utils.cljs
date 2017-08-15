(ns astrogue.utils
  (:require [cljsjs.nacl-fast :as nacl]))

(def letters {:vowels ['a 'e 'i 'o 'u 'y]
              :constonants ['b 'c 'd 'f 'g 'h 'j 'k 'l 'm 'n 'p 'q 'r 's 't 'v 'x 'z 'w]})

(def word-probability #js {"1" 10
                           "2" 5
                           "3" 1
                           "4" 1})

(def prefixes ["the good ship" "hollow asteroid" "caves of" "the neutral ship" "the chaotic ship"])

(defn choice [rng a]
  (nth a (int (* (.getUniform rng) (count a)))))

(defn generate-dungeon-name [rng]
  (clojure.string/join
    " "
    (into [(choice rng prefixes)]
          (for [word (range (js/parseInt (.getWeightedValue rng word-probability)))]
            (do
              (apply str (for [len (range (+ (int (* (.getUniform rng) 10)) 1))]
                           (do
                             (choice rng (letters (choice rng [:vowels :constonants])))))))))))

(defn dungeon-name-to-seed [d]
  (let [a (-> (js/TextEncoder. "utf8") (.encode d))
        h (js/Array.from (nacl.hash a))]
    (reduce + (map-indexed (fn [i e] (* (js/Math.pow 256 i) e)) (.slice h 0 6)))))

