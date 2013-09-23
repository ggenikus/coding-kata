(ns kata-2.core-test
  (:require [midje.sweet :refer :all]
            [kata-2.core :refer :all]))


(fact "board should generate game word, with dead cels"
      (board 2 2) => [[nil nil]
                      [nil nil]])

(fact "neighbours-of should generate neighbours for cell"
      (neighbours-of [2 2]) => [[1 1] [1 2] [1 3] [2 1] [2 3] [3 1] [3 2] [3 3]] 
       (neighbours-of [0 0]) => [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])

(fact "make-live should make specific cells live "
      (make-live (board 2 2) [[0 0]] ) =>[[(l) nil]
                                         [nil nil]]

       (make-live (board 2 2) [[0 0] [0 1] [1 0] [1 1]] ) =>[[(l) (l)] 
                                                            [(l) (l)]])

(fact "next-gen should return next-generation of living cells"

  (fact "block"
        (next-gen #{[0 0] [0 1] [1 0] [1 1]}) => #{[0 0] [0 1] [1 0] [1 1]})

  (fact "blinker"
        (next-gen #{[2 1] [2 2] [2 3]}) => #{[1 2] [2 2] [3 2]}))

