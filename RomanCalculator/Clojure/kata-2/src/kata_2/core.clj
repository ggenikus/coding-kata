(ns kata-2.core)

(def arabic-n (reverse [["I" 1] ["IV" 4] ["V" 5] ["IX" 9] ["X" 10] 
                        ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] 
                        ["CD" 400] ["D" 500] ["CM" 900] ["M" 1000]]) )

(defn value-of [num] (cond 
                       (string? num) (second (first (filter #(= num (first %)) arabic-n)))
                       (number? num) (ffirst (filter #(= num (second %)) arabic-n))))

(defn calc-arabic
  ([] nil)
  ([fst] [(value-of fst)])
  ([fst sec & others]
   (let [fst-num (value-of fst)
         sec-num (value-of sec)]
     (if (< fst-num sec-num)  
       (cons (- sec-num fst-num)
             (lazy-seq (apply calc-arabic others)))
       (cons fst-num 
             (lazy-seq (apply calc-arabic sec others)))))))

(defn roman->arabic [num] 
  (let [nums (map str num)] 
    (reduce + (apply calc-arabic nums))))

(defn calc-roman [num] 
  (let [first-mathces  (second (first (filter #(<= (second %) num) arabic-n)))] 
    (if first-mathces 
      (cons (value-of first-mathces) (lazy-seq (calc-roman (- num first-mathces))))
      nil)))

(defn arabic->roman [num]
  (apply str (calc-roman num)))




