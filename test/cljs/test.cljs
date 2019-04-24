(ns test
  (:require  [cljs.test :as t :include-macros true]
             [poe-info.item-test]
             ))

(enable-console-print!)

(t/deftest trivial-test
  (t/testing "truth"
    (t/is (= 1 1))
    )
  )

(t/run-tests 'poe-info.item-test)
