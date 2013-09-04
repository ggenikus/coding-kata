(ns kata-3.core)

; (reverse [["I" 1] ["IV" 4] ["V" 5] ["IX" 9] ["X" 10] 
                        ; ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] 
                        ; ["CD" 400] ["D" 500] ["CM" 900] ["M" 1000]])
(def roman-nums (reverse [["I" 1] ["IV" 4] ["V" 5] ["IX" 9] ["X" 10]
                 ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] ["CD" 400]
                 ["D" 500] ["CM" 900] ["M" 1000]]))

(defn value-of [num] (cond 
                       (string? num ) (second (first (filter #(= num (first %)) roman-nums)) )
                       (number? num ) (ffirst (filter #(= num (second %)) roman-nums))))

(defn roman->arabic-helper 
  ([] nil)
  ([fst] [(value-of fst)])
  ([fst sec & other] 
    (let [fst-num (value-of fst)
          sec-num (value-of sec)]
    (if (< fst-num sec-num)
      (cons (- sec-num fst-num) (lazy-seq (apply roman->arabic-helper other))) 
      (cons fst-num (lazy-seq (apply roman->arabic-helper sec other)))))))

(defn roman->arabic [num] 
  (let [nums (map str num) 
        arabic-seq (apply roman->arabic-helper nums)]
        (reduce + arabic-seq)))

(defn arabic->roman-helper [num] 
 (let [first-math (second (first (filter #(<= (second %) num ) roman-nums))) ]
    (if first-math
      (cons (value-of first-math) (arabic->roman-helper (- num first-math)))
      nil)))

(defn arabic->roman [num] (apply str (arabic->roman-helper num)))

