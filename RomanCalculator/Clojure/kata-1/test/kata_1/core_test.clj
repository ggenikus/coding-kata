(ns kata-1.core-test
  (:require [midje.sweet :refer :all]
            [kata-1.core :refer :all]))

(fact "Roman to arabic "
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

(fact "with multiple digins"
      (roman->arabic "MMMCCCXXXIII") => 3333)

(fact "arabic to roman"
      (arabic->roman 1) => "I"
      (arabic->roman 4) => "IV")

(fact "Should add romans"
      (add-roman ["I" "I"]) => "II"
      (add-roman ["I" "IV" "I"]) => "VI")

