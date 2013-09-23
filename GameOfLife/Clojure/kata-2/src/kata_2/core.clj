(ns kata-2.core)

(defn l []  :on)

(defn board [w h] (vec (repeat h (vec (repeat w nil)))))

(defn neighbours-of [[x y]]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when (not= 0 dx dy)] 
    [(+ dx x) (+ dy y)]))

(defn make-live [board cells]
  (reduce #(assoc-in %1 %2 (l)) board cells))

(defn next-gen [live-cells] 
  (let [neighbours (frequencies (mapcat neighbours-of live-cells)) 
        next-generation (for [[cell n] neighbours 
                              :when (or (= n 3) 
                                        (and (= n 2) 
                                             (live-cells cell)))]
                          cell)]
        (set next-generation)))

