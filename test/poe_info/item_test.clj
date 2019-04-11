(ns poe-info.item-test
  (:require [poe-info.item :refer :all]
            [clojure.test :refer :all]
            [poe-info.item :as item]))

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

(def worn-flask
  {:y 0,
   :implicitMods ["Creates a Smoke Cloud on Use"],
   :properties
   [{:name "Lasts %0 Seconds", :values [["5.00" 0]], :displayMode 3}
    {:name "Consumes %0 of %1 Charges on use",
     :values [["10" 0] ["30" 0]],
     :displayMode 3}
    {:name "Currently has %0 Charges", :values [["30" 0]], :displayMode 3}],
   :category {:flasks []},
   :requirements [{:name "Level", :values [["14" 0]], :displayMode 0}],
   :typeLine "Perpetual Stibnite Flask of Warding",
   :frameType 1,
   :name "",
   :w 1,
   :utilityMods ["100% increased Evasion Rating"],
   :explicitMods
   ["39% increased Charge Recovery"
    "Immune to Curses during Flask effect\nRemoves Curses on use"],
   :icon
   "https://web.poecdn.com/gen/image/WzksNCx7ImYiOiJBcnRcLzJESXRlbXNcL0ZsYXNrc1wvc3RpYm5pdGUiLCJzcCI6MC42MDg1LCJsZXZlbCI6MX1d/08305f870e/Item.png",
   :ilvl 42,
   :h 2,
   :id "17b1a09f224b337e64c570ab38c582c7ba5adcf520924d4b55917a1f362a5668",
   :inventoryId "Flask",
   :x 2,
   :identified true,
   :league "Synthesis",
   :descrText
   "Right click to drink. Can only hold charges while in belt. Refills as you kill monsters.",
   :verified false})

(deftest fixing-stash-index
  (testing "happy"
    (is (= 0 (stash-index {:inventoryId "Stash1"}))))

  (testing "full"
    (is (= 10 (stash-index conq-potency)))
    (is (= nil (stash-index worn-flask)))))

(deftest rarity-test
  (is (= :unique (rarity conq-potency)))
  (is (= :rare (rarity maelstrom-star)))
  #_(testing "rarity"))

(deftest frametype->str-test
  (testing "all"
    (is (= "Rare" (frametype->str 2)))))

(deftest properties
  (testing "flasks"
    (is (= "Consumes 30 of 60 Charges on use"
           (property->mod {:name "Consumes %0 of %1 Charges on use",
                           :values [["30" 0] ["60" 0]],
                           :displayMode 3})))
    (is (= "Lasts 4.00 Seconds" (property->mod {:name "Lasts %0 Seconds",
                                                :values [["4.00" 0]],
                                                :displayMode 3}))))

  (testing "weapon mods"
    (is (= "One Handed Mace"
           (property->mod {:name "One Handed Mace", :values [], :displayMode 0})))
    (is (= "Physical Damage: 32-74 (augmented)"
           (property->mod {:name "Physical Damage", :values [["32-74" 1]], :displayMode 0, :type 9})))
    (is (= "Critical Strike Chance: 5.00%"
           (property->mod {:name "Critical Strike Chance", :values [["5.00%" 0]], :displayMode 0, :type 12})))))

(deftest experience-reformat
  (testing "Experience number reformatting"
    (is (= "1/285,815"
           (reformat-experience "1/285815")))

    (is (= "11,111,111/2,222,222" (reformat-experience "11111111/2222222")))))

(deftest maelstrom-star-test
  (testing "Maelstrom Star Cobalt Jewel"
    (is (=
         "Rarity: Rare
Maelström Star
Cobalt Jewel
--------
Item Level: 79
--------
9% increased Damage
7% increased maximum Life
+10% to Cold and Lightning Resistances
--------
Place into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket."
         (item->str {:y 4,
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
                     :verified

                     false})))))

(deftest sapphire-flask
  (testing "Sapphire Flask"
    (is (=
         "Rarity: Normal
Sapphire Flask
--------
Lasts 4.00 Seconds
Consumes 30 of 60 Charges on use
Currently has 0 Charges
+50% to Cold Resistance
20% less Cold Damage taken
--------
Requirements:
Level: 18
--------
Item Level: 54
--------
Right click to drink. Can only hold charges while in belt. Refills as you kill monsters."

         (item->str {:y 2,
                     :properties [{:name "Lasts %0 Seconds",
                                   :values [["4.00" 0]],
                                   :displayMode 3}
                                  {:name "Consumes %0 of %1 Charges on use",
                                   :values [["30" 0] ["60" 0]],
                                   :displayMode 3}
                                  {:name "Currently has %0 Charges",
                                   :values [["0" 0]],
                                   :displayMode 3}],
                     :category {:flasks []},
                     :requirements [{:name "Level", :values [["18" 0]],
                                     :displayMode 0}],
                     :typeLine "Sapphire Flask",
                     :frameType 0,
                     :name "",
                     :w 1,
                     :utilityMods ["+50% to Cold Resistance" "20% less Cold Damage taken"],
                     :icon "https://web.poecdn.com/gen/image/WzksNCx7ImYiOiJBcnRcLzJESXRlbXNcL0ZsYXNrc1wvc2FwcGhpcmUiLCJzcCI6MC4zMDQzLCJsZXZlbCI6MH1d/4248c77db5/Item.png",
                     :ilvl 54,
                     :h 2,
                     :id "9dc8b618a250108cc5d1ef514f776f14ebd1bd34691ac66e9cf256fcf76ea30d",
                     :inventoryId "Stash3",
                     :x 4,
                     :identified true,
                     :league "Betrayal",
                     :descrText "Right click to drink. Can only hold charges while in belt. Refills as you kill monsters.",
                     :verified false})))))

(deftest fecund-vanguard-belt
  (testing "Fecund Vanguard Belt"
    (is (= "Rarity: Magic
Fecund Vanguard Belt
--------
Requirements:
Level: 78
--------
Item Level: 83
--------
+273 to Armour and Evasion Rating
--------
+92 to maximum Life"

           (item->str {:y            2,
                       :implicitMods ["+273 to Armour and Evasion Rating"],
                       :category     {:accessories ["belt"]},
                       :requirements [{:name "Level", :values [["78" 0]], :displayMode 0}],
                       :typeLine     "Fecund Vanguard Belt",
                       :frameType    1,
                       :name         "",
                       :w            2,
                       :explicitMods ["+92 to maximum Life"],
                       :icon         "https://web.poecdn.com/image/Art/2DItems/Belts/Belt7.png?scale=1&scaleIndex=1&w=2&h=1&v=3648e36de0a3f7056aca48d65e5e69d6",
                       :ilvl         83,
                       :h            1,
                       :id           "be4aee834252cfa4032245cb55c50a989f310ab7e866849d6d96bf4060e4a100",
                       :inventoryId  "Stash2",
                       :x            21,
                       :identified   true,
                       :league       "Betrayal",
                       :verified     false})))))

(deftest laviangas-wisdom
  (testing "Lavianga's Wisdom"
    (is (= "Rarity: Unique
Lavianga's Wisdom
War Hammer
--------
One Handed Mace
Physical Damage: 32-74 (augmented)
Critical Strike Chance: 5.00%
Attacks per Second: 1.40
Weapon Range: 9
--------
Requirements:
Level: 20
Str: 71
--------
Sockets: R-R
--------
Item Level: 79
--------
10% reduced Enemy Stun Threshold
--------
132% increased Physical Damage
+17 to maximum Life
+11 to maximum Mana
5% reduced Movement Speed
10% increased Area of Effect
15% increased Area Damage
--------
\"The painful memories are the easiest to recall.\"
- Lavianga, Advisor to Kaom"

           (item->str {:y             16,
                       :implicitMods  ["10% reduced Enemy Stun Threshold"],
                       :properties    [{:name "One Handed Mace", :values [], :displayMode 0}
                                       {:name "Physical Damage", :values [["32-74" 1]], :displayMode 0, :type 9}
                                       {:name "Critical Strike Chance", :values [["5.00%" 0]], :displayMode 0, :type 12}
                                       {:name "Attacks per Second", :values [["1.40" 0]], :displayMode 0, :type 13}
                                       {:name "Weapon Range", :values [["9" 0]], :displayMode 0, :type 14}],
                       :category      {:weapons ["onemace"]},
                       :requirements  [{:name "Level", :values [["20" 0]], :displayMode 0}
                                       {:name "Str", :values [["71" 0]], :displayMode 1}],
                       :typeLine      "War Hammer",
                       :flavourText   ["\"The painful memories are the easiest to recall.\"\r" "- Lavianga, Advisor to Kaom"],
                       :frameType     3,
                       :name          "Lavianga's Wisdom",
                       :w             2,
                       :explicitMods  ["132% increased Physical Damage"
                                       "+17 to maximum Life"
                                       "+11 to maximum Mana"
                                       "5% reduced Movement Speed"
                                       "10% increased Area of Effect"
                                       "15% increased Area Damage"],
                       :icon          "https://web.poecdn.com/image/Art/2DItems/Weapons/OneHandWeapons/OneHandMaces/OneHandMace3a.png?scale=1&scaleIndex=1&w=2&h=3&v=13ea05b6c91a5de5d6af2e13796321ce",
                       :ilvl          79,
                       :sockets       [{:group 0, :attr "S", :sColour "R"} {:group 0, :attr "S", :sColour "R"}],
                       :socketedItems [],
                       :h             3,
                       :id            "7eff265c564ce9406366fee5ce7415cc46ee7193a2cf1a1b6ec4bf2a826df50e",
                       :inventoryId   "Stash2",
                       :x             5,
                       :identified    true,
                       :league        "Betrayal",
                       :verified

                       false})))))

(deftest item-rarity-gem
  (testing "Item Rarity Support"
    (is (= "Rarity: Gem
Item Rarity Support
--------
Support
Level: 1
Quality: +8% (augmented)
Experience: 1/285,815
--------
Requirements:
Level: 31
Int: 52
--------
Supports any skill that can kill enemies.
--------
44% increased Rarity of Items Dropped by Enemies Slain from Supported Skills
--------
This is a Support Gem. It does not grant a bonus to your character, but to skills in sockets connected to it. Place into an item socket connected to a socket containing the Active Skill Gem you wish to augment. Right click to remove from a socket."
           (item->str {:y 19,
                       :properties [{:name "Support", :values [], :displayMode 0}
                                    {:name "Level", :values [["1" 0]], :displayMode 0, :type 5}
                                    {:name "Quality", :values [["+8%" 1]], :displayMode 0, :type 6}],
                       :category {:gems ["supportgem"]},
                       :additionalProperties [{:name "Experience",
                                               :values [["1/285815" 0]],
                                               :displayMode 2,
                                               :progress 3.4987667731911642E-6,
                                               :type 20}],
                       :requirements [{:name "Level", :values [["31" 0]], :displayMode 0}
                                      {:name "Int", :values [["52" 0]], :displayMode 1}],
                       :typeLine "Item Rarity Support",
                       :frameType 4,
                       :support true,
                       :name "",
                       :w 1,
                       :explicitMods ["44% increased Rarity of Items Dropped by Enemies Slain from Supported Skills"],
                       :icon "https://web.poecdn.com/image/Art/2DItems/Gems/Support/IncreasedQuality.png?scale=1&scaleIndex=1&w=1&h=1&v=b95626de2b8e6b73ac8fb186abbf276e",
                       :ilvl 0,
                       :h 1,
                       :secDescrText "Supports any skill that can kill enemies.",
                       :id "fff0a214fb2d8823a80182ba4a8327f1ed3ee0ebc12a31d24438ca04a86f2d79",
                       :inventoryId "Stash2",
                       :x 12,
                       :identified true,
                       :league "Betrayal",
                       :descrText "This is a Support Gem. It does not grant a bonus to your character, but to skills in sockets connected to it. Place into an item socket connected to a socket containing the Active Skill Gem you wish to augment. Right click to remove from a socket.",
                       :verified

                       false})))))

(deftest corrupted-ring
  (testing "Kraken Coil Two-Stone Ring"
    (is (= "Rarity: Rare
Kraken Coil
Two-Stone Ring
--------
Requirements:
Level: 44
--------
Item Level: 78
--------
+15% to Cold and Lightning Resistances
--------
Adds 2 to 5 Physical Damage to Attacks
+26 to Dexterity
+31 to maximum Life
+15 to maximum Mana
+9% to Lightning Resistance
+22% to Chaos Resistance
--------
Corrupted"

           (item->str {:y 16,
                       :implicitMods ["+15% to Cold and Lightning Resistances"],
                       :category {:accessories ["ring"]},
                       :requirements [{:name "Level", :values [["44" 0]], :displayMode 0}],
                       :typeLine "Two-Stone Ring",
                       :corrupted true,
                       :frameType 2,
                       :name "Kraken Coil",
                       :w 1,
                       :explicitMods ["Adds 2 to 5 Physical Damage to Attacks"
                                      "+26 to Dexterity" "+31 to maximum Life"
                                      "+15 to maximum Mana"
                                      "+9% to Lightning Resistance"
                                      "+22% to Chaos Resistance"],
                       :icon "https://web.poecdn.com/image/Art/2DItems/Rings/TopazSapphire.png?scale=1&scaleIndex=1&w=1&h=1&v=b7334698bf04b28f755467f256a3276b",
                       :ilvl 78,
                       :h 1,
                       :id "cfb67851abafa6746333440870098f84d0f6546fe2f4511e96d346096b047ffa",
                       :inventoryId "Stash2",
                       :x 18,
                       :identified true,
                       :league "Betrayal",
                       :verified

                       false})))))

(comment
  (deftest vaal-gem
    (testing "Vaal Blight"
      (is (= "Rarity: Gem
Blight
--------
Vaal, Spell, Chaos, AoE, Duration, Channelling
Level: 1
Mana Cost: 2
Cast Time: 0.30 sec
Quality: +8% (augmented)
Experience: 1/70
--------
Requirements:
Level: 1
--------
Apply a debuff to enemies in front of you which deals chaos damage over time. Enemies who aren't already debuffed by Blight are also hindered for a shorter secondary duration, slowing their movement. Continued channelling adds layers of damage to the debuff, each with their own duration.
--------
Deals 1.7 Base Chaos Damage per second
Base duration is 2.50 seconds
Base secondary duration is 0.80 seconds
Modifiers to Spell Damage apply to this Skill's Damage Over Time effect
4% increased Area of Effect
80% reduced Movement Speed
Debuff can have up to 20 layers of Damage
--------
Vaal Blight
--------
Souls Per Use: 30
Can Store 1 Use
Soul Gain Prevention: 8 sec
Cast Time: 0.60 sec
--------
Apply a powerful debuff to enemies around you which deals chaos damage over time. Then applies two additional layers in a larger area, growing greatly in size each time. Enemies are also substantially hindered for a shorter secondary duration, slowing their movement.
--------
Deals 4.3 Base Chaos Damage per second
Base duration is 6.00 seconds
Base secondary duration is 3.00 seconds
Modifiers to Spell Damage apply to this Skill's Damage Over Time effect
Modifiers to Skill Effect Duration also apply to this Skill's Soul Gain Prevention
4% increased Area of Effect
80% reduced Movement Speed
Debuff can have up to 20 layers of Damage
Hindered Enemies take 20% increased Chaos Damage
--------
Place into an item socket of the right colour to gain this skill. Right click to remove from a socket.
--------
Corrupted"
             (item->str {:y 9,
                         :properties [{:name "Vaal, Spell, Chaos, AoE, Duration, Channelling", :values [], :displayMode 0}
                                      {:name "Level", :values [["1" 0]], :displayMode 0, :type 5}
                                      {:name "Mana Cost", :values [["2" 0]], :displayMode 0}
                                      {:name "Cast Time", :values [["0.30 sec" 0]], :displayMode 0}
                                      {:name "Quality", :values [["+8%" 1]], :displayMode 0, :type 6}],
                         :category {:gems ["activegem"]},
                         :additionalProperties [{:name "Experience", :values [["1/70" 0]], :displayMode 2, :progress 0.014285714365541935, :type 20}],
                         :requirements [{:name "Level", :values [["1" 0]], :displayMode 0}],
                         :vaal {:baseTypeName "Blight",
                                :properties [{:name "Souls Per Use", :values [["30" 0]], :displayMode 0}
                                             {:name "Can Store %0 Use", :values [["1" 0]], :displayMode 3}
                                             {:name "Soul Gain Prevention", :values [["8 sec" 0]], :displayMode 0}
                                             {:name "Cast Time", :values [["0.60 sec" 0]], :displayMode 0}],
                                :explicitMods ["Deals 4.3 Base Chaos Damage per second"
                                               "Base duration is 6.00 seconds"
                                               "Base secondary duration is 3.00 seconds"
                                               "Modifiers to Spell Damage apply to this Skill's Damage Over Time effect"
                                               "Modifiers to Skill Effect Duration also apply to this Skill's Soul Gain Prevention"
                                               "4% increased Area of Effect"
                                               "80% reduced Movement Speed"
                                               "Debuff can have up to 20 layers of Damage"
                                               "Hindered Enemies take 20% increased Chaos Damage"],
                                :secDescrText "Apply a powerful debuff to enemies around you which deals chaos damage over time. Then applies two additional layers in a larger area, growing greatly in size each time. Enemies are also substantially hindered for a shorter secondary duration, slowing their movement."},
                         :typeLine "Vaal Blight",
                         :corrupted true,
                         :frameType 4,
                         :support false,
                         :name "",
                         :w 1,
                         :explicitMods ["Deals 1.7 Base Chaos Damage per second"
                                        "Base duration is 2.50 seconds"
                                        "Base secondary duration is 0.80 seconds"
                                        "Modifiers to Spell Damage apply to this Skill's Damage Over Time effect"
                                        "4% increased Area of Effect"
                                        "80% reduced Movement Speed"
                                        "Debuff can have up to 20 layers of Damage"],
                         :icon "https://web.poecdn.com/image/Art/2DItems/Gems/VaalGems/VaalBlightGem.png?scale=1&scaleIndex=1&w=1&h=1&v=6526a746f623ed6b1f9d85b11d44f39a",
                         :ilvl 0,
                         :h 1,
                         :secDescrText "Apply a debuff to enemies in front of you which deals chaos damage over time. Enemies who aren't already debuffed by Blight are also hindered for a shorter secondary duration, slowing their movement. Continued channelling adds layers of damage to the debuff, each with their own duration.",
                         :id "5cc848a5603d064c4141c008b6fe04f952b5e77397a9a243fd4896047df9ab38",
                         :inventoryId "Stash2",
                         :x 1,
                         :identified true,
                         :league "Betrayal",
                         :descrText "Place into an item socket of the right colour to gain this skill. Right click to remove from a socket.",
                         :verified false}))))))

(deftest price-strings
  (testing "price->str"
    (is (= "~b/o 1 exa" (price->str [:bo 1 :exa])))
    (is (= "~price 3 alt" (price->str [:price 3 :alt])))
    (is (= "~price 10/3 chrom" (price->str [:price 10/3 :chrom])))
    (is (thrown? java.lang.Exception (clojure.spec.alpha/check-asserts true) (price->str [:asdf 10/3 :alt])))
    
    )
  (testing "str->price"
    (is (= [:bo 1 :exa] (str->price "~b/o 1 exa")))
    (is (= [:price 3 :alt] (str->price "~price 3 alt")))
    (is (= [:price 10/3 :chrom] (str->price  "~price 10/3 chrom")))
    (is (= nil (str->price "not a price")))
    ))
