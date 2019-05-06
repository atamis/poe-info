(ns poe-info.constants)

(def stash-width 12)
(def stash-height 12)

(def normal-stash (* stash-width stash-height))
(def quad-stash (* 4 normal-stash))


(def large-inventory (* 5 12))
(def normal-inventory (* 5 11))

(def num-div-cards 251)
(def divination-stash (* 5000 num-div-cards))

(def num-essences 104)
(def essence-stash (+ (* 5000 num-essences)
                      4 ;; extra slots
                      ))

(def num-fragments (+ 4 ;; sacrifice
                      4 ;; pale court
                      4 ;; shaper
                      4 ;; uber atziri
                      1 ;; divine vessel
                      1 ;; offering
                      5 ;; breachstone
                      (* 3 ;; tiers of scarabs
                         12 ;; scarabs
                         )))
(def fragment-stash (+ (* 5000 num-fragments)
                       (* 5 99) ;; un-stacked splinters
                       ))

(def num-currencies (+ 2 ;; wisdom scroll and scrap
                       1 ;; portal scroll
                       1 ;; black whetstone
                       1 ;; armour scrap
                       1 ;; glassblower's
                       1 ;; gemcutter's
                       1 ;; chisels
                       2 ;; transmute and shard
                       2 ;; alteration and shard
                       2 ;; annul and shard
                       1 ;; chance
                       1 ;; augment
                       2 ;; exalt and shard
                       2 ;; mirror and shard
                       2 ;; regal and shard
                       2 ;; alch and shard
                       2 ;; chaos and shard
                       1 ;; blessed
                       1 ;; divine
                       1 ;; jeweller
                       1 ;; fusing
                       1 ;; chrome
                       1 ;; perandus
                       1 ;; silver
                       1 ;; scour
                       1 ;; regret
                       1 ;; vaal
                       3 ;; sextants
                       ))

(def currency-stash (+ (* 5000 num-currencies)
                       15 ;; extra slots
                       ))
