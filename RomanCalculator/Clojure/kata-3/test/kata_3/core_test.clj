(ns kata-3.core-test
  (:require [midje.sweet :refer :all]
            [kata-3.core :refer :all]))

(fact "roman->arabic should turn roman num to arabic"
    (fact "should evaluate basic roman nums"
        (roman->arabic "I") => 1
        (roman->arabic "IV") => 4
        (roman->arabic "V") => 5
        (roman->arabic "IX") => 9 
        (roman->arabic "X") => 10
        (roman->arabic "XL") => 40
        (roman->arabic "L") => 50
        (roman->arabic "XC") => 90
        (roman->arabic "C") => 100
        (roman->arabic "CD") => 400
        (roman->arabic "D") => 500
        (roman->arabic "CM") => 900
        (roman->arabic "M") => 1000)
    (fact "should evaluate combination of basic roman nums"
          (fact "should use roman->arabic-helper with splite num"
                (roman->arabic "II") => truthy 
                (provided
                  (roman->arabic-helper "I" "I") => [1 1]))
          (roman->arabic "II") => 2 
          (roman->arabic "IVI") => 5 
          (roman->arabic "MMMCCCXXXIII") => 3333))

(fact " roman->arabic-helper should use value-of"
      (roman->arabic-helper "I") => truthy
      (provided
        (value-of "I") => 1))

(fact " arabic->roman-helper should use value-of"
      (arabic->roman-helper 1) => truthy
      (provided
        (value-of 1) => "I"))

(fact "arabic->roman should turn arabic to roman"
      (fact " arabic->roman should use arabic->roman helper"
            (arabic->roman "I") => truthy 
            (provided 
              (arabic->roman-helper "I") => [1]))
      (arabic->roman 1) => "I"
      (arabic->roman 2) => "II"
      (arabic->roman 3333) => "MMMCCCXXXIII"
      )



