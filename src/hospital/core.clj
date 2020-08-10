(ns hospital.core
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))

(let [hospital-da-ester (h.model/novo-hospital)]
  (pprint hospital-da-ester))

(pprint h.model/fila_vazia)

