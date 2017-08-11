(ns ^:figwheel-no-load astrogue.dev
  (:require
    [astrogue.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
