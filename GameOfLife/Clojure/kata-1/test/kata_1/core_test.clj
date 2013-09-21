(ns kata-1.core-test
  (:require [midje.sweet :refer :all]
            [kata-1.core :refer :all]))


(fact "game-of-life should take list of list and return another one"
      (game-of-life [[(d) (d) (d) (d) (d) (d) (d) (d)]
                     [(d) (d) (d) (d) (d) (d) (d) (d)]
                     [(d) (d) (d) (d) (d) (d) (d) (d)]
                     [(d) (d) (d) (d) (d) (d) (d) (d)]]) => [[(d) (d) (d) (d) (d) (d) (d) (d)] 
                                                     [(d) (d) (d) (d) (d) (d) (d) (d)] 
                                                     [(d) (d) (d) (d) (d) (d) (d) (d)] 
                                                     [(d) (d) (d) (d) (d) (d) (d) (d)]])

(fact "game-of-life test"
      (game-of-life [[(d) (d) (d) (d) (d) (d) (d) (d)]
                     [(d) (d) (d) (d) (l) (d) (d) (d)]
                     [(d) (d) (d) (l) (l) (d) (d) (d)]
                     [(d) (d) (d) (d) (d) (d) (d) (d)]]) => [[(d) (d) (d) (d) (d) (d) (d) (d)] 
                                                     [(d) (d) (d) (l) (l) (d) (d) (d)] 
                                                     [(d) (d) (d) (l) (l) (d) (d) (d)] 
                                                     [(d) (d) (d) (d) (d) (d) (d) (d)]])



(fact "game-of life should use update-field"
      (game-of-life ..field..) => truthy
      (provided 
        (reduce next-step anything anything) => [[0 0]] ))

(fact "next-step should use update-field and calculate"
      (next-step ..field.. ..coordinates..) => ..updated-field..
      (provided
        (calculate ..field.. ..coordinates..) => ..calculated-field..
        (update-field ..field.. ..coordinates.. ..calculated-field..) => ..updated-field.. 
        ..updated-field.. =contains=> [] ))

(fact "updated-field"
      (update-field [[0 0] [0 0]] [0 0] 1) => [[1 0] [0 0]]
      (update-field [[0 0] [0 0]] [0 1] 1) => [[0 1] [0 0]]
      (update-field [[0 0] [0 0]] [1 0] 1) => [[0 0] [1 0]]
      (update-field [[0 0] [0 0]] [1 1] 1) => [[0 0] [0 1]] )

; (fact "calculate shoud evaluate nighbors and apply rules based on cell and neighbors state"
;   (calculate ..field.. ..coords..) => ..evaluated-cell..
;   (provided 
;     (neighbors-of ..coords..) => [..n1.. ..n2..] 
;     (get-cell ..field.. ..coords..) => ..cell.. 
;     [(get-cell ..field.. ..n1..) (get-cell ..gield.. ..n2..)] => ..neighbors-cells..

;     (apply-game-rules ..cell.. ..neighbors-cells..) => ..evaluated-cell..
;     ..evaluated-cell.. =contains=> 0
;     ))

(fact "neighbors-of should return neighbors of some coordinates"
      (neighbors-of [0 0]) => #{[0 1] [1 0] [1 1]}
      (neighbors-of [0 1]) => #{[0 0] [0 2] [1 0] [1 1] [1 2]}
      (neighbors-of [2 2]) => #{[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]})

(def field [[9 8 7]
            [6 5 4]])

(fact "get-cell should return cell form fild"
      (get-cell field [0 0]) => 9 
      (get-cell field [0 1]) => 8 
      (get-cell field [0 2]) => 7 
      (get-cell field [1 0]) => 6 
      (get-cell field [1 1]) => 5 
      (get-cell field [1 2]) => 4)

(fact "live"
      (live? (d)) => false
      (live? (l)) => true )

(fact "apply-game-rules"
      (fact " Any live cell with fewer than two live neighbours dies, as if caused by underpopulation."
            (apply-game-rules (l) [(d) (d) (l)]) => (d) )
      (fact "Any live cell with more than three live neighbours dies, as if by overcrowding."
            (apply-game-rules (l) [(l) (l) (l) (l) (d)]) => (d)) 
      (fact "Any live cell with two or three live neighbours lives on to the next generation."
            (apply-game-rules (l) [(l) (l) (l) (d)]) => (l))
      (fact "Any dead cell with exactly three live neighbours becomes a live cell."
            (apply-game-rules (d) [(l) (l) (l) (d)]) => (l))
      )


