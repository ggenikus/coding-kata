(ns kata-4.core)

(def roman-n (reverse  [["I" 1] ["IV" 4]["V" 5] ["IX" 9] ["X" 10] 
              ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] ["CD" 400] 
              ["D" 500] ["CM" 900] ["M" 1000]]))

(defn value-of [num] (cond 
                       (string? num) (second (first (filter #(= num (first %)) roman-n )))
                       (number? num) (ffirst (filter #(= num (second %)) roman-n))))

(defn roman->arabic 
  ([] nil)
  ([fst] [(value-of fst)])
  ([fst sec & rst ] 
   (let [fnum (value-of fst) 
         secnum (value-of sec)]
         (if (< fnum secnum)
           (cons (- secnum fnum) (lazy-seq (apply roman->arabic rst)))
           (cons fnum (lazy-seq (apply roman->arabic sec rst)))))))

(defn arabic->roman [num] 
  (let [fst-mathes (ffirst (filter #(<= (second %) num) roman-n))] 
    (if fst-mathes
      (cons fst-mathes (lazy-seq (arabic->roman (- num (value-of fst-mathes)))))
      nil
      )))

(defn add-roman [num]
  (let [nums (map str num)
        arabic (reduce +  (apply roman->arabic nums))]
        (apply str (arabic->roman arabic))))
