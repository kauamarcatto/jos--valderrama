(ns calculador-trastes.core)

; Calcula a posição de todos os trastes dado o comprimento da escala (em mm)
; e o número de trastes desejado.
;
; A fórmula da afinação temperada para o traste n é:
;   distancia-pestana(n) = escala - escala / 2^(n/12)
;   distancia-ponte(n)   = escala / 2^(n/12)
;
; Verificação: no traste 12, distancia-pestana deve ser exatamente escala/2
;   ex: escala 650mm → traste 12 = 325mm
;
; Retorna uma sequência de mapas, um por traste:
;   {:traste 1, :distancia-pestana 36.48, :distancia-ponte 613.52}
(defn calcular-trastes [escala num-trastes]
  (for [n (range 1 (inc num-trastes))]           ; itera de 1 até num-trastes
    (let [distancia-ponte   (/ escala             ; comprimento vibrante restante
                               (Math/pow 2 (/ n 12)))
          distancia-pestana (- escala             ; quanto já foi "cortado" da escala
                               distancia-ponte)]
      {:traste            n
       :distancia-pestana (double distancia-pestana)
       :distancia-ponte   (double distancia-ponte)})))

; Ponto de entrada da aplicação — roda ao executar: clj -M:run
(defn -main [& _args]
  (let [escala 650
        trastes (calcular-trastes escala 24)]
    (println (str "Escala: " escala " mm"))
    (println "---")
    (doseq [{:keys [traste distancia-pestana distancia-ponte]} trastes]
      (println (format "Traste %2d | Pestana: %7.2f mm | Ponte: %7.2f mm"
                       traste distancia-pestana distancia-ponte)))))
