(ns kata-1.core)

(defn update-field [field coord value ]
  (assoc-in field coord value))

(defn add-coord [[x1 y1] [x2 y2]] [(+ x1 x2) (+ y1 y2)])

(defn positive? [x] (>= x 0))

(defn neighbors-of [coord] 
  (let [steps [[-1 1] [0 1] [1 1] [1 0] 
               [1 -1] [0 -1] [-1 -1] [-1 0]]] 
    (set (filter (fn [[x y]] (and (positive? x) 
                                  (positive? y))) 
                 (map (partial add-coord coord) steps)))))
(defn d [] ".")
(defn l [] "*")

(defn get-cell ([field coord] (get-in field coord)))

(defn live? [cell] (= (l) cell))

  

(defn apply-game-rules [cell neighbors-cells]
  (let [number-of-live-cells (count (filter (partial = (l)) neighbors-cells))
        number-of-died-cells (count (filter (partial = (d)) neighbors-cells))]
    (cond (< number-of-live-cells 2 ) (d) 
          (> number-of-live-cells 3) (d)
          (and (live? cell) (some #{number-of-live-cells} [2 3])) (l)
          (and (not (live? cell)) (= number-of-live-cells 3)) (l)
          :else cell
    )))

(defn calculate [field coords] 
  (let [neighbors (neighbors-of coords)
        cell (get-cell field coords)
        neighbors-cells (filter (complement nil?) (map (partial get-cell field) neighbors)) ]
        (apply-game-rules cell neighbors-cells)))

(defn next-step [field coord] 
  (update-field field coord (calculate field coord) ))

(defn game-of-life [field] 
  (let [ x-coords (range 0 (count field))
         y-coords (range 0 (count (first field)))
         x-y-coords (for [x x-coords
                          y y-coords]
                      [x y])]
     (reduce #(update-field %1 %2 (calculate field %2) ) field x-y-coords)))

