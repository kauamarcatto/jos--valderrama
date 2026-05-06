(ns calculador-trastes.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [calculador-trastes.core :refer [calcular-trastes]]))

(deftest traste-12-e-metade-da-escala
  (testing "O 12º traste deve estar exatamente na metade da escala"
    (let [escala 650
          trastes (calcular-trastes escala 12)
          traste-12 (last trastes)]
      (is (< (Math/abs (- (:distancia-pestana traste-12) 325.0)) 0.01)))))
