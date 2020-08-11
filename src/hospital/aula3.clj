(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))


(defn testa-atomao []
  (let [hospital-silveira (atom {:espera h.model/fila_vazia})]
    (println hospital-silveira)
    (pprint hospital-silveira)
    (pprint (deref hospital-silveira))
    ;atalho pro deref
    (pprint @hospital-silveira)

    ;não é assim que eu altero o conteudo de um atomo
    (pprint (assoc @hospital-silveira :laboratorio1 h.model/fila_vazia))

    ; essa é uma das maneiras de alterar contúdo dentro de um átomo
    (swap! hospital-silveira assoc :laboratorio1 h.model/fila_vazia)
    (pprint hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio2 h.model/fila_vazia)
    (pprint hospital-silveira)

    ;update tradicional imutavel, com dereferencia, que não trara efeito
    (update @hospital-silveira :laboratorio1 conj "111")

    ;indo pra swap
    (swap! hospital-silveira update :laboratorio1 conj "111")
    (pprint hospital-silveira)))

;(testa-atomao)



(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "após inserir pessoa" pessoa))

(defn simula-um-dia-em-paralelo1
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;forçando situações de retry
;(simula-um-dia-em-paralelo1)


(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "após inserir pessoa" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;sem forçar situação de retry (busy retry), pode acontecer, mas pode não acontecer
(simula-um-dia-em-paralelo)






