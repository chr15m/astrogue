(ns ^:figwheel-no-load ld39.dev
  (:require
    [ld39.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
