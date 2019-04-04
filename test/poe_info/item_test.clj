(ns poe-info.item-test
  (:require [poe-info.item :as item]
            [clojure.test :refer :all]))

(def conq-potency
  {:y 4,
   :properties [{:name "Limited to", :values [["1" 0]], :displayMode 0}],
   :category {:jewels []},
   :typeLine "Cobalt Jewel",
   :flavourText ["What you earn is almost as important as what you take."],
   :stash-id 10,
   :frameType 3,
   :name "Conqueror's Potency",
   :w 1,
   :explicitMods
   ["4% increased Effect of your Curses"
    "8% increased Effect of Flasks on you"
    "3% increased effect of Non-Curse Auras from your Skills"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Jewels/HighQuestRewardBlue.png?scale=1&w=1&h=1&v=625d2c5e3b92ef2bb1551d909bec6456",
   :ilvl 1,
   :h 1,
   :id "0a6733da59d3ffd16742c549222ae6a6324ea26b4f63a7a374bbbf23820e4d2e",
   :inventoryId "Stash11",
   :x 9,
   :identified true,
   :league "Synthesis",
   :descrText
   "Place into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket.",
   :verified false})

(def maelstrom-star
  {:y 4,
   :category {:jewels []},
   :typeLine "Cobalt Jewel",
   :frameType 2,
   :name "Maelström Star",
   :w 1,
   :explicitMods ["9% increased Damage" "7% increased maximum Life" "+10% to Cold and Lightning Resistances"],
   :icon "https://web.poecdn.com/image/Art/2DItems/Jewels/basicint.png?scale=1&scaleIndex=1&w=1&h=1&v=cd579ea22c05f1c6ad2fd015d7a710bd",
   :ilvl 79,
   :h 1,
   :id "1f70992ca6673553534bc224bd9395f9bde084615a05cddc45ad3c2d43935801",
   :inventoryId "Stash2",
   :x 5,
   :identified true,
   :league "Betrayal",
   :descrText "Place into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket.",
   :verified false})

(deftest fixing-stash-index
  (testing "happy"
    (is (= 0 (item/stash-index {:inventoryId "Stash1"}))))
  (testing "full"
    (is (= 10 (item/stash-index conq-potency)))))

(deftest rarity
  (is (= :unique (item/rarity conq-potency)))
  (is (= :rare (item/rarity maelstrom-star)))
  #_(testing "rarity"))
