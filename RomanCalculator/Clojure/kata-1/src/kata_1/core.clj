(ns kata-1.core)

(def roman-nums (array-map 
                  "I" 1 "IV" 4 "V" 5 "IX" 9 "X" 10 
                 "XL" 40 "L" 50 "XC" 90 "C" 100 
                 "CD" 400 "D" 500 "CM" 900 "M" 1000 ))

(defn value-of [n] (roman-nums n 0))

(defn roman->arabic-helper [num] 
  (loop [[f s & more :as all] num acc 0]
    (cond (empty? all) acc
          (and f (not s)) (+ acc f)
          (< f s) (recur more (+ acc (- s f)))
          :else (recur (rest all) (+ acc f)))))

(defn arabic->roman-seq [num]
  (let [roman-nums-rev (apply array-map (flatten (reverse roman-nums)))
        first-mathces  (first (filter #(<= (val %) num) roman-nums-rev))]
    (if (nil? first-mathces)
      nil
      (cons (key first-mathces) (lazy-seq (arabic->roman (- num (val first-mathces))))))))

(defn arabic->roman [num] (apply str (arabic->roman-seq num)))

(defn roman->arabic [num]
  (let [nums (map str num)] 
    (roman->arabic-helper (map value-of nums))))

(defn add-roman [nums]
  (let [arabic-nums (map roman->arabic nums)] 
    (->> arabic-nums 
         (apply +)
         arabic->roman)))
      

