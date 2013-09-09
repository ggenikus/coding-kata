(ns kata-6.core)

(def roman-n (reverse  [["I" 1] 
                         ["IV" 4] ["V" 5] ["IX" 9] ["X" 10] 
                         ["XL" 40] ["L" 50] ["XC" 90] ["C" 100] ["CD" 400] 
                         ["D" 500] ["CM" 900] ["M" 1000]]))


(defn value-of [num] (cond 
                       (string? num ) (second (first (filter #(= num (first %)) roman-n)))
                       (number? num ) (ffirst (filter #(= num (second %)) roman-n))))

(defn roman->arabic 
  ([] nil)
  ([frst] [(value-of frst)])
  ([frst sec & other] 
   (let [frstnum (value-of frst)
         secnum (value-of sec)]
         (if (< frstnum secnum)
           (cons (- secnum frstnum) (lazy-seq (apply roman->arabic other)))
           (cons frstnum (lazy-seq (apply roman->arabic sec other)))))))

(defn arabic->roman [num] 
  (let [frst-mathces (ffirst  (filter #(<= (second %) num) roman-n))]
        (when frst-mathces 
          (cons frst-mathces (lazy-seq (arabic->roman (- num (value-of frst-mathces))))))))

(defn roman-adder [num]
  (let [nums (map str num)
        arabic (reduce + (apply roman->arabic nums))
        romans (arabic->roman arabic)]
        (apply str romans)))
