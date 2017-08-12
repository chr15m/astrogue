(ns astrogue.yaba)

(def letters {:vowels ['a 'e 'i 'o 'u 'y]
              :constonants ['b 'c 'd 'f 'g 'h 'j 'k 'l 'm 'n 'p 'q 'r 's 't 'v 'x 'z 'w]})

(defn encode [n]
  (print "encode" n)
  (let [alphabet (for [v (letters :vowels) c (letters :constonants)] (if (= (mod n 2) 0) (str c v) (str v c)))
        l (count alphabet)]
    (loop [current n out "" i 0]
      (if (> current 0)
        (let [remainder (rem current l)
              new-value (quot current l)
              word (nth alphabet remainder)]
          (recur new-value (str out word) (inc i)))
        out))))

(defn decode [s]
  (print "decode" s)
  (let [words (partition 2 s)
        order (nth (nth words 0) 0)
        order (contains? (set (map str (letters :vowels))) order)
        alphabet (for [v (letters :vowels) c (letters :constonants)] (if order (str v c) (str c v)))
        l (count alphabet)
        pows (map #(js/Math.pow l %) (range l))
        values (map (fn [e] (.indexOf alphabet (apply str e))) words)
        over (map-indexed (fn [i e] (* (nth pows i) e)) values)]
    (reduce + over)))

(comment
  ; tests
  (print (to-yaba 0xffffffffffff))
  (print (from-yaba "atudodupycepus"))
  (let [;r 269019326326388
        r (js/Math.floor (* (js/Math.random) 0xffffffffffff))
        y (to-yaba r)
        b (from-yaba y)]
    (print r)
    (print y)
    (print b)
    (assert (= b r))))

