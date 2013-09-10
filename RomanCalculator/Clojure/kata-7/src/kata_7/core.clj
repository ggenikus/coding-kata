(ns kata-7.core)

(def roman-n (reverse  [["I" 1] 
                         ["IV" 4] ["V" 5] ["IX" 9] ["X" 10] 
                         ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] ["CD" 400] 
                         ["D" 500] ["CM" 900] ["M" 1000]]))


(defn value-of [num] (cond (string? num) (second (first (filter #(= num (first %)) roman-n)))
                           (number? num) (ffirst (filter #(= num (second %)) roman-n))))

(defn roman->arabic 
  ([] nil)
  ([fst] [(value-of fst)])
  ([fst sec & other]
   (let [fstnum (value-of fst)
         secnum (value-of sec)]
         (if (< fstnum secnum)
          (cons (- secnum fstnum) (lazy-seq (apply roman->arabic other)))
          (cons fstnum (lazy-seq (apply roman->arabic sec other)))))))

(defn arabic->roman [num] 
  (let [firstmatchs (ffirst (filter #(<= (second %) num) roman-n))]
    (when firstmatchs 
      (cons firstmatchs (lazy-seq (arabic->roman (- num (value-of firstmatchs))))))))

(defn add-roman [num] 
  (let [nums (map str num)
        arabic (reduce + (apply roman->arabic nums))] 
    (apply str (arabic->roman arabic))))