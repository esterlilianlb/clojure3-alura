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
    (pprint hospital-silveira)

    ))

(testa-atomao)
