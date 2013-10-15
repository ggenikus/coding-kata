(ns kata-4.core-test
  (:require [midje.sweet :refer :all]
            [kata-4.core :refer :all]
            [clojure.core.typed :as t]))


(fact "should generate empty field"
      (field 1 1) => [[nil]]
      (field 2 2) => [[nil nil]
                      [nil nil]])

(fact "should evaluate next field based on current state"
      (next-gen #{[0 0] [0 1] [1 0] [1 1]}) =>
      #{[0 0] [0 1] [1 0] [1 1]})




(t/check-ns 'kata-4.core)

























