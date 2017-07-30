(ns ld39.prod
  (:require
    [ld39.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
