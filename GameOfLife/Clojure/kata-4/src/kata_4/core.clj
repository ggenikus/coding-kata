(ns kata-4.core
  (:require [clojure.core.typed :as t]))


(t/def-alias Field (t/Vec (t/Vec nil)))
(t/def-alias Width t/Int)
(t/def-alias Height t/Int)
(t/def-alias Coord '[t/Int t/Int])
(t/def-alias Cell Coord)


(t/ann field [Width Height -> Field])
(defn field  [w h]
  (vec (repeat h (vec (repeat w nil)))))


(t/ann neighbours [Coord -> (t/Seq Coord)])
(defn- neighbours [[x y]]
  (t/for> :- Coord
           [dx :- t/Int [-1 0 1]
           dy :- t/Int [-1 0 1]
           :when (not (= dx dy 0))]
          [(+ dx x) (+ dy y)]))

(t/ann ^:no-check frequencies-t [(t/Seq Any) -> (t/Map Cell t/Int)])
(defn frequencies-t [c] (frequencies c))

(t/ann next-gen [(t/Set Cell) -> (t/Set Cell)])
(defn next-gen [live-cells]
  (let [neighbours (mapcat neighbours live-cells)
        freq-neghbours (frequencies-t neighbours)

        next-cells (t/for> :- Cell
                          [[cell freq] :- '[Cell t/Int] freq-neghbours
                           :when (or (= 3 freq)
                                   (and (= 2 freq)
                                        (live-cells cell)))]
                     cell)]
    (set next-cells)))



