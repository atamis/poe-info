(ns poe-info.item-test
  (:require [poe-info.item :refer :all]
            [clojure.test :refer :all]
            [poe-info.item :as item]))

;; To collapse all
;; z m


;; TODO: These item classes
;; fractured corrupted abyss jewel
;; unset ring
;; currency with stack values (shift click to unstack when stack size > 1)
;; abyss sockets
;; prophecies
;; corrupted maps (with quality)
;; shield
;; flask quality
;; vaal gem with quality
;; talisman (have tier)
;; synthesized (Hate Ward Synthesised Crypt Armor in b14)
;; shaped/elder items
;; unidentified corrupted maps

;; TODO:
;; whisper buy gems with quality
;; whisper buy things with no price

(clojure.spec.alpha/check-asserts true)

(def ^:const conq-potency
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
(def ^:const maelstrom-star
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
(def ^:const worn-flask
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
(def ^:const maelstrom-star-data
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
Place into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket.")
(def ^:const maelstrom-star-api
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
   :verified

   false})
(def ^:const saphire-flask-data
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
Right click to drink. Can only hold charges while in belt. Refills as you kill monsters.")
(def ^:const saphire-flask-api
  {:y 2,
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
   :verified false})
(def ^:const fecund-vanguard-belt-api
  {:y            2,
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
   :verified     false})
(def ^:const laviangas-wisdom-data
  "Rarity: Unique
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
- Lavianga, Advisor to Kaom")
(def ^:const laviangas-wisdom-api
  {:y             16,
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

   false})
(def ^:const item-rarity-gem-data
  "Rarity: Gem
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
This is a Support Gem. It does not grant a bonus to your character, but to skills in sockets connected to it. Place into an item socket connected to a socket containing the Active Skill Gem you wish to augment. Right click to remove from a socket.")
(def ^:const item-rarity-gem-api
  {:y 19,
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

   false})
(def ^:const corrupted-ring-data
  "Rarity: Rare
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
Corrupted")
(def ^:const corrupted-ring-api
  {:y 16,
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

   false})
(def ^:const vaal-blight-data
  "Rarity: Gem
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
Corrupted")
(def ^:const vaal-blight-api
  {:y 9,
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
   :verified false})
(def ^:const fractured-armor-data
  "Rarity: Rare
Rift Veil
Astral Plate
--------
Armour: 711
--------
Requirements:
Level: 62
Str: 180
--------
Sockets: R R-R-R
--------
Item Level: 78
--------
+9% to all Elemental Resistances
--------
9.6 Life Regenerated per second (fractured)
+41 to Strength
+73 to maximum Life
+25% to Cold Resistance
Reflects 40 Physical Damage to Melee Attackers
--------
Fractured Item")
(def ^:const fractured-armor-api
  {:y 20,
   :implicitMods ["+9% to all Elemental Resistances"],
   :properties [{:name "Armour", :values [["711" 0]], :displayMode 0, :type 16}],
   :category {:armour ["chest"]},
   :requirements
   [{:name "Level", :values [["62" 0]], :displayMode 0}
    {:name "Str", :values [["180" 0]], :displayMode 1}],
   :typeLine "Astral Plate",
   :frameType 2,
   :name "Rift Veil",
   :w 2,
   :explicitMods
   ["+41 to Strength"
    "+73 to maximum Life"
    "+25% to Cold Resistance"
    "Reflects 40 Physical Damage to Melee Attackers"],
   :fracturedMods ["9.6 Life Regenerated per second"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/BodyArmours/BodyStr1C.png?scale=1&w=2&h=3&fractured=1&v=890da62b76971297e9eacccd44123e99",
   :ilvl 78,
   :sockets
   [{:group 0, :attr "S", :sColour "R"}
    {:group 1, :attr "S", :sColour "R"}
    {:group 1, :attr "S", :sColour "R"}
    {:group 1, :attr "S", :sColour "R"}],
   :socketedItems [],
   :h 3,
   :id "70492077169396a63dd10d0fc4ec30b96d054e033e80d0e6493c6a1da821cfe1",
   :fractured true,
   :inventoryId "Stash2",
   :x 20,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const corrupted-fractured-weapon-data
  "Rarity: Rare
Dragon Bane
Dragoon Sword
--------
One Handed Sword
Physical Damage: 36-75 (augmented)
Elemental Damage: 46-106 (augmented), 1-42 (augmented)
Critical Strike Chance: 6.00%
Attacks per Second: 1.81 (augmented)
Weapon Range: 12
--------
Requirements:
Level: 72
Dex: 220
--------
Sockets: G G-G
--------
Item Level: 79
--------
20% chance to cause Bleeding on Hit
--------
Adds 1 to 42 Lightning Damage (fractured)
21% increased Attack Speed (fractured)
+50 to Dexterity
Adds 4 to 9 Physical Damage
Adds 46 to 106 Fire Damage
--------
Corrupted
--------
Fractured Item")
(def ^:const corrupted-fractured-weapon-api
  {:y 0,
   :implicitMods ["20% chance to cause Bleeding on Hit"],
   :properties
   [{:name "One Handed Sword", :values [], :displayMode 0}
    {:name "Physical Damage", :values [["36-75" 1]], :displayMode 0, :type 9}
    {:name "Elemental Damage",
     :values [["46-106" 4] ["1-42" 6]],
     :displayMode 0,
     :type 10}
    {:name "Critical Strike Chance",
     :values [["6.00%" 0]],
     :displayMode 0,
     :type 12}
    {:name "Attacks per Second", :values [["1.81" 1]], :displayMode 0, :type 13}
    {:name "Weapon Range", :values [["12" 0]], :displayMode 0, :type 14}],
   :category {:weapons ["onesword"]},
   :requirements
   [{:name "Level", :values [["72" 0]], :displayMode 0}
    {:name "Dex", :values [["220" 0]], :displayMode 1}],
   :typeLine "Dragoon Sword",
   :corrupted true,
   :frameType 2,
   :name "Dragon Bane",
   :w 1,
   :explicitMods
   ["+50 to Dexterity"
    "Adds 4 to 9 Physical Damage"
    "Adds 46 to 106 Fire Damage"],
   :fracturedMods ["Adds 1 to 42 Lightning Damage" "21% increased Attack Speed"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Weapons/OneHandWeapons/Rapiers/Rapier9.png?scale=1&w=1&h=4&fractured=1&v=8545ae716901cb1ea3c518d5e5630ba6",
   :ilvl 79,
   :sockets
   [{:group 0, :attr "D", :sColour "G"}
    {:group 1, :attr "D", :sColour "G"}
    {:group 1, :attr "D", :sColour "G"}],
   :socketedItems [],
   :h 4,
   :id "f70dc35d7a4298304cdf538081ff16aea9511f367dd67dddd2388a4137663350",
   :fractured true,
   :inventoryId "Stash40",
   :x 7,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const unided-weapon-data
  "Rarity: Rare
Jewelled Foil
--------
One Handed Sword
Physical Damage: 32-60
Critical Strike Chance: 5.50%
Attacks per Second: 1.60
Weapon Range: 12
--------
Requirements:
Dexterity: 212
--------
Sockets: G-G G
--------
Item Level: 81
--------
+25% to Global Critical Strike Multiplier
--------
Unidentified")
(def ^:const unided-weapon-api
  {:y 6,
   :implicitMods ["+25% to Global Critical Strike Multiplier"],
   :properties
   [{:name "One Handed Sword", :values [], :displayMode 0}
    {:name "Physical Damage", :values [["32-60" 0]], :displayMode 0, :type 9}
    {:name "Critical Strike Chance",
     :values [["5.50%" 0]],
     :displayMode 0,
     :type 12}
    {:name "Attacks per Second", :values [["1.60" 0]], :displayMode 0, :type 13}
    {:name "Weapon Range", :values [["12" 0]], :displayMode 0, :type 14}],
   :category {:weapons ["onesword"]},
   :requirements [{:name "Dexterity", :values [["212" 0]], :displayMode 1}],
   :typeLine "Jewelled Foil",
   :frameType 2,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Weapons/OneHandWeapons/Rapiers/Rapier7.png?scale=1&w=1&h=4&v=d4a9ff1bbae62362d78bc8d5a4484446",
   :ilvl 81,
   :sockets
   [{:group 0, :attr "D", :sColour "G"}
    {:group 0, :attr "D", :sColour "G"}
    {:group 1, :attr "D", :sColour "G"}],
   :socketedItems [],
   :h 4,
   :id "2d7fd894bbfb87a97105b9b40f29f35b705df8083c41ada0547912ece189f4aa",
   :inventoryId "Stash2",
   :x 3,
   :identified false,
   :league "Synthesis",
   :verified false})
(def ^:const prophecy-data
  "Rarity: Normal
The Jeweller's Touch
--------
The Jeweller leaves five fingerprints and connects them with a single thread.
--------
You will create a fully-linked five-socket item with a single Jeweller's Orb
--------
Right-click to add this prophecy to your character.")
(def ^:const prophecy-api
  {:y 10,
   :category {:currency []},
   :typeLine "The Jeweller's Touch",
   :flavourText
   ["The Jeweller leaves five fingerprints and connects them with a single thread."],
   :frameType 8,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Currency/ProphecyOrbRed.png?scale=1&w=1&h=1&v=dc9105d2b038a79c7c316fc2ba30cef0",
   :ilvl 0,
   :prophecyText
   "You will create a fully-linked five-socket item with a single Jeweller's Orb",
   :note "~b/o 22 chaos",
   :h 1,
   :id "42186252336d0a4c47e8b518283cd8cb58e5a9017cef026de72bf36a1b7c6042",
   :inventoryId "Stash15",
   :x 7,
   :identified true,
   :league "Synthesis",
   :descrText "Right-click to add this prophecy to your character.",
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
         maelstrom-star-data
         (item->str maelstrom-star-api)))))

(deftest sapphire-flask-test
  (testing "Sapphire Flask"
    (is (=
         saphire-flask-data
         (item->str saphire-flask-api)))))

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

           (item->str fecund-vanguard-belt-api)))))

(deftest laviangas-wisdom
  (testing "Lavianga's Wisdom"
    (is (= laviangas-wisdom-data
           (item->str laviangas-wisdom-api)))))

(deftest item-rarity-gem
  (testing "Item Rarity Support"
    (is (= item-rarity-gem-data
           (item->str item-rarity-gem-api)))))

(deftest corrupted-ring
  (testing "Kraken Coil Two-Stone Ring"
    (is (= corrupted-ring-data

           (item->str corrupted-ring-api)))))

(deftest vaal-gem
  (testing "Vaal Blight"
    (is (= vaal-blight-data
           (item->str vaal-blight-api)))))

(deftest fractured-armor
  (testing "Rift Veil Astral Plate"
    (is (= fractured-armor-data
           (item->str fractured-armor-api)))))

(deftest corrupted-fractured-weapon
  (testing "Dragon Bane Dragoon Sword"
    (is (= corrupted-fractured-weapon-data
           (item->str corrupted-fractured-weapon-api)))))

(deftest unided-weapon-test
  (testing "Jewelled Foil"
    (is (= unided-weapon-data
           (item->str unided-weapon-api)))))

(deftest prophecy-test
  (testing "The Jeweller's Touch"
    (is (= prophecy-data
           (item->str prophecy-api)))))

(deftest price-strings
  (testing "price->str"
    (is (= "~b/o 1 exa" (price->str [:bo 1 :exa])))
    (is (= "~price 3 alt" (price->str [:price 3 :alt])))
    (is (= "~price 10/3 chrom" (price->str [:price 10/3 :chrom])))
    (is (thrown? java.lang.Exception (clojure.spec.alpha/check-asserts true) (price->str [:asdf 10/3 :alt]))))

  (testing "str->price"
    (is (= [:bo 1 :exa] (str->price "~b/o 1 exa")))
    (is (= [:price 3 :alt] (str->price "~price 3 alt")))
    (is (= [:price 10/3 :chrom] (str->price  "~price 10/3 chrom")))
    (is (= nil (str->price "not a price")))))

(deftest whisper-text-test
  (testing "whisper text"
    (is (=
         "@LabbJugg Hi, I would like to buy your Dragon Bane Dragoon Sword listed for 10 chaos in Synthesis (stash tab \"frac1\"; position: left 8, top 1)"
         (make-whisper "LabbJugg" [:bo 10 :chaos] "frac1" corrupted-fractured-weapon-api)))
    (is (=
         "@LabbJugg Hi, I would like to buy your Jewelled Foil listed for 1 chaos in Synthesis (stash tab \"44\"; position: left 4, top 7)"
         (make-whisper "LabbJugg" [:bo 1 :chaos] "44" unided-weapon-api)))))

(clojure.spec.alpha/check-asserts true)
