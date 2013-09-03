(ns kata-2.core-test
  (:require [midje.sweet :refer :all]
            [kata-2.core :refer :all]))


(fact "shuld call calc-arabic"
      (roman->arabic "IIV") => 5 
      (provided 
        (calc-arabic "I" "I" "V") => [1 4]))

(fact "arabic-> roman should turn arabic to roman" 
      (arabic->roman 1) => "I")

(fact "calc-arabic should turn roman to arabic"
      (calc-arabic "I") => [1]
      (calc-arabic "I" "I") => [1 1]
      (calc-arabic "I" "I" "V") => [1 4] 
      (calc-arabic "I" "V") => [4] )

