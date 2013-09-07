(ns kata-5.core-test
  (:require [midje.sweet :refer :all]
            [kata-5.core :refer :all]))

(fact "add-roman spec"
      (add-roman "IIVI") => "VI"
      (provided
        (roman->arabic "I" "I" "V" "I") => [1 4 1]
        (arabic->roman 6) => ["V" "I"]))

(fact "roman->arabic shoudl turn roman to arabic, and use value of"

      (fact "should use value-of for one argument"
            (roman->arabic "I") => [1]
            (provided
              (value-of "I") => 1))

      (roman->arabic ) => nil

      (roman->arabic "I") => [1]

      (fact "should use value-of for more then one argument"
            (roman->arabic "I" "X") => [9]
            (provided
              (value-of "I") => 1
              (value-of "X") => 10))

      (roman->arabic "I" "I") => [1 1] 
      (roman->arabic "I" "V") => [4])

(fact "arabic->roman should turn arbic to roman"
      (fact "arabic->roman should use value-of"
            (arabic->roman 1) => ["I"]
            (provided 
              (value-of "I") => 1))
      (arabic->roman 7) => ["V" "I" "I"])


(fact "value of should find value for given greek key"
      (value-of "I") => 1 
      (value-of "IV") => 4
      (value-of "V") => 5
      (value-of "IX") => 9
      (value-of "X") => 10
      (value-of "XL") => 40
      (value-of "L") => 50
      (value-of "XC") => 90
      (value-of "C") => 100
      (value-of "CD") => 400
      (value-of "D") => 500
      (value-of "CM") => 900
      (value-of "M") => 1000)


(fact "value of should find value for given roman key"
      (value-of  1 ) =>"I"
      (value-of  4) =>"IV"
      (value-of  5) =>"V"
      (value-of  9) =>"IX"
      (value-of  10) =>"X"
      (value-of  40) =>"XL"
      (value-of  50) =>"L"
      (value-of  90) =>"XC"
      (value-of  100) =>"C"
      (value-of  400) =>"CD"
      (value-of  500) =>"D"
      (value-of  900) =>"CM"
      (value-of  1000) =>"M")



