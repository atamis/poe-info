(ns poe-info.item-test
  #?@
   (:clj
    [(:require
      [clojure.spec.alpha :as s]
      [clojure.test :as t]
      [poe-info.item :as i])]
    :cljs
    [(:require
      [cljs.spec.alpha :as s]
      [cljs.test :as t :include-macros true]
      [poe-info.item :as i])]))

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
;; Cluster jewel

;; TODO:
;; whisper buy gems with quality
;; whisper buy things with no price


(s/check-asserts true)

;; Note: trailing spaces in string literals are "intentional",
;; and shouldn't be removed.
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
(def ^:const fecund-vanguard-belt-data
  "Rarity: Magic
Fecund Vanguard Belt
--------
Requirements:
Level: 78
--------
Item Level: 83
--------
+273 to Armour and Evasion Rating
--------
+92 to maximum Life")
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
(def ^:const unset-ring-data
  "Rarity: Rare
Vengeance Circle
Unset Ring
--------
Requirements:
Level: 57
--------
Sockets: B
--------
Item Level: 74
--------
Has 1 Socket
--------
Adds 7 to 12 Physical Damage to Attacks
+229 to Accuracy Rating
+52 to maximum Mana
+43% to Fire Resistance
+33% to Lightning Resistance
13% increased Damage (crafted)")
(def ^:const unset-ring-api
  {:y 7,
   :implicitMods ["Has 1 Socket"],
   :category {:accessories ["ring"]},
   :requirements [{:name "Level", :values [["57" 0]], :displayMode 0}],
   :typeLine "Unset Ring",
   :frameType 2,
   :name "Vengeance Circle",
   :w 1,
   :explicitMods
   ["Adds 7 to 12 Physical Damage to Attacks"
    "+229 to Accuracy Rating"
    "+52 to maximum Mana"
    "+43% to Fire Resistance"
    "+33% to Lightning Resistance"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Rings/Empty-Socket.png?scale=1&w=1&h=1&v=3671f6d79d7190b43879b830e63fc078",
   :ilvl 74,
   :sockets [{:group 0, :attr "I", :sColour "B"}],
   :socketedItems [],
   :h 1,
   :id "9b5461389547fc887196db181acdb0d451e6024faa1e29a55e8f9245d520835b",
   :craftedMods ["13% increased Damage"],
   :inventoryId "Stash11",
   :x 9,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const exalted-orb-data
  "Rarity: Currency
Exalted Orb
--------
Stack Size: 1/10
--------
Enchants a rare item with a new random modifier
--------
Right click this item then left click a rare item to apply it. Rare items can have up to six random modifiers.")
(def ^:const exalted-orb-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["1/10" 0]], :displayMode 0}],
   :category {:currency []},
   :typeLine "Exalted Orb",
   :frameType 5,
   :name "",
   :w 1,
   :explicitMods ["Enchants a rare item with a new random modifier"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Currency/CurrencyAddModToRare.png?scale=1&w=1&h=1&v=1745ebafbd533b6f91bccf588ab5efc5",
   :ilvl 0,
   :stackSize 1,
   :maxStackSize 5000,
   :h 1,
   :id "88836669f8db2c24187ea30c80d8a935b9d9bb552e67c7fa5e6a6ca41ffd3eb8",
   :inventoryId "Stash1",
   :x 11,
   :identified true,
   :league "Synthesis",
   :descrText
   "Right click this item then left click a rare item to apply it. Rare items can have up to six random modifiers.",
   :verified false})
(def ^:const jewellers-big-stack-data
  "Rarity: Currency
Jeweller's Orb
--------
Stack Size: 4,554/20
--------
Reforges the number of sockets on an item
--------
Right click this item then left click a socketed item to apply it. The item's quality increases the chances of obtaining more sockets.
Shift click to unstack.")
(def ^:const jewellers-big-stack-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["4554/20" 0]], :displayMode 0}],
   :category {:currency []},
   :typeLine "Jeweller's Orb",
   :frameType 5,
   :name "",
   :w 1,
   :explicitMods ["Reforges the number of sockets on an item"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Currency/CurrencyRerollSocketNumbers.png?scale=1&w=1&h=1&v=2946b0825af70f796b8f15051d75164d",
   :ilvl 0,
   :stackSize 4554,
   :maxStackSize 5000,
   :h 1,
   :id "0b7e7d6221e1164479e380683e20f6411a891a416d293c6a3e20cf8e77d3b1c1",
   :inventoryId "Stash1",
   :x 19,
   :identified true,
   :league "Synthesis",
   :descrText
   "Right click this item then left click a socketed item to apply it. The item's quality increases the chances of obtaining more sockets.",
   :verified false})
(def ^:const currency-shard-data
  "Rarity: Currency
Chaos Shard
--------
Stack Size: 2/20
--------
A stack of 20 shards becomes a Chaos Orb.
Shift click to unstack.")
(def ^:const currency-shard-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["2/20" 0]], :displayMode 0}],
   :category {:currency []},
   :typeLine "Chaos Shard",
   :frameType 5,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Currency/ChaosShard.png?scale=1&w=1&h=1&v=c206269aeda3a6a7b5a8ac110045afca",
   :ilvl 0,
   :stackSize 2,
   :maxStackSize 20,
   :h 1,
   :id "d156f4d12888bce4a6d833823805d8df3b83bb3ab2faa9ee7a02d95af39bbd83",
   :inventoryId "Stash1",
   :x 49,
   :identified true,
   :league "Synthesis",
   :descrText "A stack of 20 shards becomes a Chaos Orb.",
   :verified false})
(def ^:const lightpoacher-data
  "Rarity: Unique
Lightpoacher
Great Crown
--------
Quality: +10% (augmented)
Armour: 157 (augmented)
Energy Shield: 31 (augmented)
--------
Requirements:
Level: 53
Str: 59
Int: 59
--------
Sockets: R R A A
--------
Item Level: 80
--------
Has 2 Abyssal Sockets
Trigger Level 20 Spirit Burst when you Use a Skill while you have a Spirit Charge
+13% to all Elemental Resistances
+1 to Maximum Spirit Charges per Abyss Jewel affecting you
20% chance to gain a Spirit Charge on Kill
Recover 4% of Life when you lose a Spirit Charge
--------
The eyes of the living hold a glimmer of hope.
Don't waste it.")
(def ^:const lightpoacher-api
  {:y 10,
   :properties
   [{:name "Quality", :values [["+10%" 1]], :displayMode 0, :type 6}
    {:name "Armour", :values [["157" 1]], :displayMode 0, :type 16}
    {:name "Energy Shield", :values [["31" 1]], :displayMode 0, :type 18}],
   :category {:armour ["helmet"]},
   :requirements
   [{:name "Level", :values [["53" 0]], :displayMode 0}
    {:name "Str", :values [["59" 0]], :displayMode 1}
    {:name "Int", :values [["59" 0]], :displayMode 1}],
   :typeLine "Great Crown",
   :flavourText
   ["The eyes of the living hold a glimmer of hope.\r" "Don't waste it."],
   :frameType 3,
   :name "Lightpoacher",
   :w 2,
   :explicitMods
   ["Has 2 Abyssal Sockets"
    "Trigger Level 20 Spirit Burst when you Use a Skill while you have a Spirit Charge"
    "+13% to all Elemental Resistances"
    "+1 to Maximum Spirit Charges per Abyss Jewel affecting you"
    "20% chance to gain a Spirit Charge on Kill"
    "Recover 4% of Life when you lose a Spirit Charge"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/Helmets/AbyssHelmet.png?scale=1&w=2&h=2&v=c7383e609399bafe70ad70944133931b",
   :ilvl 80,
   :sockets
   [{:group 0, :attr "S", :sColour "R"}
    {:group 1, :attr "S", :sColour "R"}
    {:group 2, :attr "A", :sColour "A"}
    {:group 3, :attr "A", :sColour "A"}],
   :socketedItems [],
   :h 2,
   :id "afa39569ddba8f46ec958d75b8e647cf1ef7d936ebfbcc91d71398db1c4a3f52",
   :inventoryId "Stash7",
   :x 6,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const tombfist-data
  "Rarity: Unique
Tombfist
Steelscale Gauntlets
--------
Armour: 65
Evasion Rating: 65
--------
Requirements:
Level: 36
Str: 29
Dex: 29
--------
Sockets: R-R-G A
--------
Item Level: 78
--------
Has 1 Abyssal Socket
6% increased maximum Life
With a Murderous Eye Jewel Socketed, Intimidate Enemies for 4 seconds on Hit with Attacks
With a Searching Eye Jewel Socketed, Maim Enemies for 4 seconds on Hit with Attacks
--------
\"Ever watched a man crush another man's skull?
Hard to feel hope after that.\"")
(def ^:const tombfist-api
  {:y 5,
   :properties
   [{:name "Armour", :values [["65" 0]], :displayMode 0, :type 16}
    {:name "Evasion Rating", :values [["65" 0]], :displayMode 0, :type 17}],
   :category {:armour ["gloves"]},
   :requirements
   [{:name "Level", :values [["36" 0]], :displayMode 0}
    {:name "Str", :values [["29" 0]], :displayMode 1}
    {:name "Dex", :values [["29" 0]], :displayMode 1}],
   :typeLine "Steelscale Gauntlets",
   :flavourText
   ["\"Ever watched a man crush another man's skull?\r"
    "Hard to feel hope after that.\""],
   :frameType 3,
   :name "Tombfist",
   :w 2,
   :explicitMods
   ["Has 1 Abyssal Socket"
    "6% increased maximum Life"
    "With a Murderous Eye Jewel Socketed, Intimidate Enemies for 4 seconds on Hit with Attacks"
    "With a Searching Eye Jewel Socketed, Maim Enemies for 4 seconds on Hit with Attacks"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/Gloves/AbyssGloves.png?scale=1&w=2&h=2&v=752cb15c222d082b9c8ec4cacf444422",
   :ilvl 78,
   :sockets
   [{:group 0, :attr "S", :sColour "R"}
    {:group 0, :attr "S", :sColour "R"}
    {:group 0, :attr "D", :sColour "G"}
    {:group 1, :attr "A", :sColour "A"}],
   :socketedItems [],
   :h 2,
   :id "d2d002f82fef5db8fa30293111cff6c5ef576263c5ae54c1b4c800eaabfe489f",
   :inventoryId "Stash7",
   :x 2,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const darkness-enthroned-data
  "Rarity: Unique
Darkness Enthroned
Stygian Vise
--------
Sockets: A A
--------
Item Level: 80
--------
Has 1 Abyssal Socket
--------
Has 1 Abyssal Socket
50% increased Effect of Socketed Jewels
--------
Hold in your hand the darkness
and never will the light blind you.")
(def ^:const darkness-enthroned-api
  {:y 7,
   :implicitMods ["Has 1 Abyssal Socket"],
   :category {:accessories ["belt"]},
   :typeLine "Stygian Vise",
   :flavourText
   ["Hold in your hand the darkness\r" "and never will the light blind you."],
   :frameType 3,
   :name "Darkness Enthroned",
   :w 2,
   :explicitMods
   ["Has 1 Abyssal Socket" "50% increased Effect of Socketed Jewels"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Belts/DarknessEnthroned.png?scale=1&w=2&h=1&v=da21305836a0b67861183f9b9c040a93",
   :ilvl 80,
   :sockets
   [{:group 0, :attr "A", :sColour "A"} {:group 1, :attr "A", :sColour "A"}],
   :socketedItems [],
   :h 1,
   :id "e54fe439e198f901a785e3e07e76cad5c79a551123c4ab276da2b9adc236ffc9",
   :inventoryId "Stash7",
   :x 0,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const atziris-mirror-data
  "Rarity: Unique
Atziri's Mirror
Golden Buckler
--------
Quality: +5% (augmented)
Chance to Block: 25%
Evasion Rating: 902 (augmented)
--------
Requirements:
Level: 54
Dex: 130
--------
Sockets: G G
--------
Item Level: 70
--------
6% increased Movement Speed
--------
+50 to Intelligence
186% increased Evasion Rating
+29% to all Elemental Resistances
50% reduced Duration of Curses on you
Curse Reflection
+10% Chance to Block Attack Damage while not Cursed
+20% Chance to Block Spell Damage while Cursed
--------
\"As long as I see death in my mirror, so will Wraeclast.\"
- Atziri, Queen of the Vaal")
(def ^:const atziris-mirror-api
  {:y 10,
   :implicitMods ["6% increased Movement Speed"],
   :properties
   [{:name "Quality", :values [["+5%" 1]], :displayMode 0, :type 6}
    {:name "Chance to Block", :values [["25%" 0]], :displayMode 0, :type 15}
    {:name "Evasion Rating", :values [["902" 1]], :displayMode 0, :type 17}],
   :category {:armour ["shield"]},
   :requirements
   [{:name "Level", :values [["54" 0]], :displayMode 0}
    {:name "Dex", :values [["130" 0]], :displayMode 1}],
   :typeLine "Golden Buckler",
   :flavourText
   ["\"As long as I see death in my mirror, so will Wraeclast.\"\r"
    "- Atziri, Queen of the Vaal"],
   :frameType 3,
   :name "Atziri's Mirror",
   :w 2,
   :explicitMods
   ["+50 to Intelligence"
    "186% increased Evasion Rating"
    "+29% to all Elemental Resistances"
    "50% reduced Duration of Curses on you"
    "Curse Reflection"
    "+10% Chance to Block Attack Damage while not Cursed"
    "+20% Chance to Block Spell Damage while Cursed"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/Shields/ShieldDex5Unique.png?scale=1&w=2&h=2&v=f9a1a33ba289aa3c90592c257a5d7714",
   :ilvl 70,
   :sockets
   [{:group 0, :attr "D", :sColour "G"} {:group 1, :attr "D", :sColour "G"}],
   :socketedItems [],
   :h 2,
   :id "334c198bc927411a45341dff8d4fba39db9a3e8d5489a694b97bdbbf9d3552ea",
   :inventoryId "Stash6",
   :x 4,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const witchfire-data
  "Rarity: Unique
Witchfire Brew
Stibnite Flask
--------
Quality: +26% (augmented)
Lasts 6.30 (augmented) Seconds
Consumes 15 (augmented) of 30 Charges on use
Currently has 0 Charges
100% increased Evasion Rating
--------
Requirements:
Level: 48
--------
Item Level: 75
--------
Creates a Smoke Cloud on Use
--------
50% increased Charges used
26% increased Damage Over Time during Flask Effect
Grants Level 21 Despair Curse Aura during Flask Effect
--------
\"Think of those that cursed us, judged us, 
and burned our sisters upon the pyre. 
Think of their names as you drink, 
and even their children will feel what we do to them today.\" 
-Vadinya, to her coven
--------
Right click to drink. Can only hold charges while in belt. Refills as you kill monsters.")
(def ^:const witchfire-api
  {:y 8,
   :implicitMods ["Creates a Smoke Cloud on Use"],
   :properties
   [{:name "Quality", :values [["+26%" 1]], :displayMode 0, :type 6}
    {:name "Lasts %0 Seconds", :values [["6.30" 1]], :displayMode 3}
    {:name "Consumes %0 of %1 Charges on use",
     :values [["15" 1] ["30" 0]],
     :displayMode 3}
    {:name "Currently has %0 Charges", :values [["0" 0]], :displayMode 3}],
   :category {:flasks []},
   :requirements [{:name "Level", :values [["48" 0]], :displayMode 0}],
   :typeLine "Stibnite Flask",
   :flavourText
   ["\"Think of those that cursed us, judged us, \r"
    "and burned our sisters upon the pyre. \r"
    "Think of their names as you drink, \r"
    "and even their children will feel what we do to them today.\" \r"
    "-Vadinya, to her coven"],
   :frameType 3,
   :name "Witchfire Brew",
   :w 1,
   :utilityMods ["100% increased Evasion Rating"],
   :explicitMods
   ["50% increased Charges used"
    "26% increased Damage Over Time during Flask Effect"
    "Grants Level 21 Despair Curse Aura during Flask Effect"],
   :icon
   "https://web.poecdn.com/gen/image/WzksNCx7ImYiOiJBcnRcLzJESXRlbXNcL0ZsYXNrc1wvV2l0Y2hGaXJlQnJldyIsInNwIjowLjYwODUsImxldmVsIjowfV0/198a094502/Item.png",
   :ilvl 75,
   :h 2,
   :id "364ca547f692405ad118c005c5393f4bc57aae474034cf2dfccc0ad240a73348",
   :inventoryId "Stash12",
   :x 3,
   :identified true,
   :league "Synthesis",
   :descrText
   "Right click to drink. Can only hold charges while in belt. Refills as you kill monsters.",
   :verified false})
(def ^:const qual-white-map-data
  "Rarity: Normal
Superior Plaza Map
--------
Map Tier: 14
Item Quantity: +20% (augmented)
Quality: +20% (augmented)
--------
Item Level: 81
--------
Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.")
(def ^:const qual-white-map-api
  {:y 3,
   :properties
   [{:name "Map Tier", :values [["14" 0]], :displayMode 0, :type 1}
    {:name "Item Quantity", :values [["+20%" 1]], :displayMode 0, :type 2}
    {:name "Quality", :values [["+20%" 1]], :displayMode 0, :type 6}],
   :category {:maps []},
   :typeLine "Superior Plaza Map",
   :frameType 0,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/Atlas2Maps/New/Plaza.png?scale=1&w=1&h=1&mn=3&mt=14&v=9f06fb7975b9f77fb338e1c710f77153",
   :ilvl 81,
   :h 1,
   :id "88a8a25dce7f6ea539f501908e7a9610b042d215c7f9a062b90e4dbe91eebf39",
   :inventoryId "Stash6",
   :x 11,
   :identified true,
   :league "Synthesis",
   :descrText
   "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.",
   :verified false})
(def ^:const corrupted-unid-map-data
  "Rarity: Rare
Superior Pit of the Chimera Map
--------
Map Tier: 16
Item Quantity: +20% (augmented)
Quality: +20% (augmented)
--------
Item Level: 83
--------
Unidentified
--------
Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.
--------
Corrupted")
(def ^:const corrupted-unid-map-api
  {:y 2,
   :properties
   [{:name "Map Tier", :values [["16" 0]], :displayMode 0, :type 1}
    {:name "Item Quantity", :values [["+20%" 1]], :displayMode 0, :type 2}
    {:name "Quality", :values [["+20%" 1]], :displayMode 0, :type 6}],
   :category {:maps []},
   :typeLine "Superior Pit of the Chimera Map",
   :corrupted true,
   :frameType 2,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/Atlas2Maps/New/Chimera.png?scale=1&w=1&h=1&mn=3&mt=0&v=ff4e81347a05767fd71e04b14924a8c0",
   :ilvl 83,
   :h 1,
   :id "3d4db4b5b2b4b1e6f4bf1e0c6bf1dd9e32b5df95db67c4235a693d58fed17413",
   :inventoryId "Stash6",
   :x 10,
   :identified false,
   :league "Synthesis",
   :descrText
   "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.",
   :verified false})
(def ^:const corrupted-map-data
  "Rarity: Rare
Blood Roost
Arsenal Map
--------
Map Tier: 11
Item Quantity: +104% (augmented)
Item Rarity: +62% (augmented)
Monster Pack Size: +40% (augmented)
--------
Item Level: 78
--------
27% more Magic Monsters
23% more Rare Monsters
Players are Cursed with Temporal Chains
43% more Monster Life
Monsters have 100% increased Area of Effect
Monsters deal 90% extra Damage as Cold
Rare Monsters each have a Nemesis Mod
Magic Monster Packs each have a Bloodline Mod
Slaying Enemies close together has a 13% chance to attract monsters from Beyond
Players cannot Regenerate Life, Mana or Energy Shield
--------
Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.
--------
Corrupted")
(def ^:const corrupted-map-api
  {:y 4,
   :properties
   [{:name "Map Tier", :values [["11" 0]], :displayMode 0, :type 1}
    {:name "Item Quantity", :values [["+104%" 1]], :displayMode 0, :type 2}
    {:name "Item Rarity", :values [["+62%" 1]], :displayMode 0, :type 3}
    {:name "Monster Pack Size", :values [["+40%" 1]], :displayMode 0, :type 4}],
   :category {:maps []},
   :typeLine "Arsenal Map",
   :corrupted true,
   :frameType 2,
   :name "Blood Roost",
   :w 1,
   :explicitMods
   ["27% more Magic Monsters"
    "23% more Rare Monsters"
    "Players are Cursed with Temporal Chains"
    "43% more Monster Life"
    "Monsters have 100% increased Area of Effect"
    "Monsters deal 90% extra Damage as Cold"
    "Rare Monsters each have a Nemesis Mod"
    "Magic Monster Packs each have a Bloodline Mod"
    "Slaying Enemies close together has a 13% chance to attract monsters from Beyond"
    "Players cannot Regenerate Life, Mana or Energy Shield"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/Atlas2Maps/New/Arsenal.png?scale=1&w=1&h=1&mn=3&mt=11&v=07aae23d9c7fa518864ce0466977a772",
   :ilvl 78,
   :h 1,
   :id "53e307cb3a63f76ecc761f9f86425724688d9a5521e6032b14698b03062f2483",
   :inventoryId "Stash6",
   :x 10,
   :identified true,
   :league "Synthesis",
   :descrText
   "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.",
   :verified false})
(def ^:const rare-map-data
  "Rarity: Rare
Desecrated Secrets
Palace Map
--------
Map Tier: 11
Item Quantity: +66% (augmented)
Item Rarity: +28% (augmented)
Monster Pack Size: +18% (augmented)
Quality: +20% (augmented)
--------
Item Level: 83
--------
Area is inhabited by 2 additional Rogue Exiles
Players are Cursed with Vulnerability
Monsters reflect 18% of Elemental Damage
Unique Boss has 35% increased Life
Unique Boss has 70% increased Area of Effect
--------
Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.")
(def ^:const rare-map-api
  {:y 2,
   :properties
   [{:name "Map Tier", :values [["11" 0]], :displayMode 0, :type 1}
    {:name "Item Quantity", :values [["+66%" 1]], :displayMode 0, :type 2}
    {:name "Item Rarity", :values [["+28%" 1]], :displayMode 0, :type 3}
    {:name "Monster Pack Size", :values [["+18%" 1]], :displayMode 0, :type 4}
    {:name "Quality", :values [["+20%" 1]], :displayMode 0, :type 6}],
   :category {:maps []},
   :typeLine "Palace Map",
   :frameType 2,
   :name "Desecrated Secrets",
   :w 1,
   :explicitMods
   ["Area is inhabited by 2 additional Rogue Exiles"
    "Players are Cursed with Vulnerability"
    "Monsters reflect 18% of Elemental Damage"
    "Unique Boss has 35% increased Life"
    "Unique Boss has 70% increased Area of Effect"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/Atlas2Maps/New/Palace.png?scale=1&w=1&h=1&mn=3&mt=11&v=e32d570548554f453f7b4c371cfd19b3",
   :ilvl 83,
   :h 1,
   :id "0480cf1ebcb60cff465390b0771ec279944d19bc12da1ad9a21f56dae4937c47",
   :inventoryId "Stash6",
   :x 11,
   :identified true,
   :league "Synthesis",
   :descrText
   "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.",
   :verified false})
(def ^:const whakawairua-tuahu-data
  "Rarity: Unique
Whakawairua Tuahu
Strand Map
--------
Map Tier: 2
Item Quantity: +40% (augmented)
Item Rarity: +123% (augmented)
--------
Item Level: 80
--------
Area contains many Totems
Curses have 50% reduced effect on Monsters
Rare Monsters each have a Nemesis Mod
--------
We all began life in darkness, we shall all end it there.
--------
Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.")
(def ^:const whakawairua-tuahu-api
  {:y 3,
   :properties
   [{:name "Map Tier", :values [["2" 0]], :displayMode 0, :type 1}
    {:name "Item Quantity", :values [["+40%" 1]], :displayMode 0, :type 2}
    {:name "Item Rarity", :values [["+123%" 1]], :displayMode 0, :type 3}],
   :category {:maps []},
   :typeLine "Strand Map",
   :flavourText ["We all began life in darkness, we shall all end it there."],
   :frameType 3,
   :name "Whakawairua Tuahu",
   :w 1,
   :explicitMods
   ["Area contains many Totems"
    "Curses have 50% reduced effect on Monsters"
    "Rare Monsters each have a Nemesis Mod"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/UniqueMapEye.png?scale=1&w=1&h=1&v=6ab3c0f5b018f6553bd8400063918883",
   :ilvl 80,
   :h 1,
   :id "55fe84963734e6c8d8463b76b10761179b7b81770db9e441343c0c4ef2f81602",
   :inventoryId "Stash4",
   :x 19,
   :identified true,
   :league "Synthesis",
   :descrText
   "Travel to this Map by using it in the Templar Laboratory or a personal Map Device. Maps can only be used once.",
   :verified false})
(def ^:const quality-vaal-gem-data
  "Rarity: Gem
Spectral Throw
--------
Vaal, Attack, Projectile
Level: 1
Mana Cost: 7
Effectiveness of Added Damage: 54%
Quality: +20% (augmented)
Experience: 1/70
--------
Requirements:
Level: 1
--------
Throws a spectral copy of your melee weapon. It flies out and then returns to you, in a spinning attack that strikes enemies in its path.
--------
Deals 54% of Base Damage
10% increased Attack Speed
--------
Vaal Spectral Throw
--------
Souls Per Use: 10
Can Store 3 Uses
Soul Gain Prevention: 6 sec
Effectiveness of Added Damage: 91%
--------
Throws a spectral copy of your melee weapon. It spirals out in a spinning attack that strikes enemies in its path.
--------
Deals 91% of Base Damage
Fires 4 additional Projectiles
10% increased Attack Speed
Can't be Evaded
--------
Place into an item socket of the right colour to gain this skill. Right click to remove from a socket.
--------
Corrupted")
(def ^:const quality-vaal-gem-api
  {:y 10,
   :properties
   [{:name "Vaal, Attack, Projectile", :values [], :displayMode 0}
    {:name "Level", :values [["1" 0]], :displayMode 0, :type 5}
    {:name "Mana Cost", :values [["7" 0]], :displayMode 0}
    {:name "Effectiveness of Added Damage", :values [["54%" 0]], :displayMode 0}
    {:name "Quality", :values [["+20%" 1]], :displayMode 0, :type 6}],
   :category {:gems ["activegem"]},
   :additionalProperties
   [{:name "Experience",
     :values [["1/70" 0]],
     :displayMode 2,
     :progress 0.014285714365541935,
     :type 20}],
   :requirements [{:name "Level", :values [["1" 0]], :displayMode 0}],
   :vaal
   {:baseTypeName "Spectral Throw",
    :properties
    [{:name "Souls Per Use", :values [["10" 0]], :displayMode 0}
     {:name "Can Store %0 Uses", :values [["3" 0]], :displayMode 3}
     {:name "Soul Gain Prevention", :values [["6 sec" 0]], :displayMode 0}
     {:name "Effectiveness of Added Damage",
      :values [["91%" 0]],
      :displayMode 0}],
    :explicitMods
    ["Deals 91% of Base Damage"
     "Fires 4 additional Projectiles"
     "10% increased Attack Speed"
     "Can't be Evaded"],
    :secDescrText
    "Throws a spectral copy of your melee weapon. It spirals out in a spinning attack that strikes enemies in its path."},
   :typeLine "Vaal Spectral Throw",
   :corrupted true,
   :frameType 4,
   :support false,
   :name "",
   :w 1,
   :explicitMods ["Deals 54% of Base Damage" "10% increased Attack Speed"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Gems/VaalGems/VaalGhostlyThrow.png?scale=1&w=1&h=1&v=5bca0e233696a6917ef179426cabfbd3",
   :ilvl 0,
   :h 1,
   :secDescrText
   "Throws a spectral copy of your melee weapon. It flies out and then returns to you, in a spinning attack that strikes enemies in its path.",
   :id "f3add3dc01ec22a6ca5e9654631fee10324e5b116df7177dbcea8b9355d554f8",
   :inventoryId "Stash2",
   :x 2,
   :identified true,
   :league "Synthesis",
   :descrText
   "Place into an item socket of the right colour to gain this skill. Right click to remove from a socket.",
   :verified false})
(def ^:const abyss-jewel-data
  "Rarity: Rare
Ancient Weaver
Murderous Eye Jewel
--------
Abyss
--------
Requirements:
Level: 49
--------
Item Level: 76
--------
Adds 18 to 27 Fire Damage to Staff Attacks (fractured)
Adds 10 to 17 Cold Damage to Staff Attacks
+10 to Strength and Intelligence
+2% Chance to Block Attack Damage if you were Damaged by a Hit Recently
--------
Place into an Abyssal Socket on an Item or into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket.
--------
Corrupted
--------
Fractured Item")
(def ^:const abyss-jewel-api
  "This is a corrupted fractured abyss jewel, so it hits a lot of different categories."
  {:y 0,
   :properties [{:name "Abyss", :values [], :displayMode 0}],
   :category {:jewels ["abyss"]},
   :requirements [{:name "Level", :values [["49" 0]], :displayMode 0}],
   :typeLine "Murderous Eye Jewel",
   :corrupted true,
   :frameType 2,
   :name "Ancient Weaver",
   :w 1,
   :explicitMods
   ["Adds 10 to 17 Cold Damage to Staff Attacks"
    "+10 to Strength and Intelligence"
    "+2% Chance to Block Attack Damage if you were Damaged by a Hit Recently"],
   :fracturedMods ["Adds 18 to 27 Fire Damage to Staff Attacks"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Jewels/MurderousEye.png?scale=1&w=1&h=1&fractured=1&v=6679365e95eb89fd626cf59fa2b57c99",
   :ilvl 76,
   :h 1,
   :id "181a0f111bcff2452c0140a9731fd8466e20a24bc3f5a539a671946ded11e067",
   :fractured true,
   :inventoryId "Stash40",
   :abyssJewel true,
   :x 5,
   :identified true,
   :league "Synthesis",
   :descrText
   "Place into an Abyssal Socket on an Item or into an allocated Jewel Socket on the Passive Skill Tree. Right click to remove from the Socket.",
   :verified false})
(def ^:const simple-frag-data
  "Rarity: Normal
Sacrifice at Dawn
--------
Only those who aspire can dare to hope.
--------
Can be used in the Templar Laboratory or a personal Map Device.")
(def ^:const simple-frag-api
  {:y 10,
   :category {:maps ["fragment"]},
   :typeLine "Sacrifice at Dawn",
   :flavourText ["Only those who aspire can dare to hope."],
   :frameType 0,
   :name "",
   :w 1,
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/Vaal02.png?scale=1&w=1&h=1&v=3ead6455599ec6c303f54ba98d6f8eb2",
   :ilvl 0,
   :h 1,
   :id "48709e29ef37980205a9d6bc220dd2cd5c328101bcd50c7c43f91436ca31a455",
   :inventoryId "Stash2",
   :x 11,
   :identified true,
   :league "Synthesis",
   :descrText "Can be used in the Templar Laboratory or a personal Map Device.",
   :verified false})
(def ^:const divine-vessel-data
  "Rarity: Normal
Divine Vessel
--------
Unique Boss deals 10% increased Damage
Unique Boss has 10% increased Attack and Cast Speed
Unique Boss has 10% increased Life
Unique Boss has 20% increased Area of Effect
--------
Power is a curious thing. 
It can be contained, hidden, locked away, 
and yet it always breaks free.
--------
Can be used in the Templar Laboratory or a personal Map Device, allowing you to capture the Soul of the Map's Boss. The Vessel containing the captured Soul can be retrieved from the Map Device. You must be in the Map when the boss is defeated.")
(def ^:const divine-vessel-api
  {:y 12,
   :category {:maps ["fragment"]},
   :typeLine "Divine Vessel",
   :flavourText
   ["Power is a curious thing. \r"
    "It can be contained, hidden, locked away, \r"
    "and yet it always breaks free."],
   :frameType 0,
   :name "",
   :w 1,
   :explicitMods
   ["Unique Boss deals 10% increased Damage\r\nUnique Boss has 10% increased Attack and Cast Speed\r\nUnique Boss has 10% increased Life\r\nUnique Boss has 20% increased Area of Effect"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Maps/SinFlaskEmpty.png?scale=1&w=1&h=2&v=8b9f566d35bc00387e43f8ec4eefad31",
   :ilvl 0,
   :h 2,
   :id "31422ece1c160fbaf311d87045b5fa804e923db3610fbbd5aa5bd5cb20a78126",
   :inventoryId "Stash3",
   :x 1,
   :identified true,
   :league "Synthesis",
   :descrText
   "Can be used in the Templar Laboratory or a personal Map Device, allowing you to capture the Soul of the Map's Boss. The Vessel containing the captured Soul can be retrieved from the Map Device. You must be in the Map when the boss is defeated.",
   :verified false})
(def ^:const talisman-data
  "Rarity: Rare
Phoenix Gorget
Mandible Talisman
--------
Requirements:
Level: 43
--------
Item Level: 74
--------
Talisman Tier: 1
--------
9% increased Attack and Cast Speed
--------
+85 to maximum Life
+44 to maximum Mana
+13% to all Elemental Resistances
+23% to Fire Resistance
+21% to Cold Resistance (crafted)
--------
The First Ones hold us
between two sharpened blades.
That should we stray too far from the path,
we find ourselves severed.
- The Wolven King
--------
Corrupted")
(def ^:const talisman-api
  {:y 23,
   :implicitMods ["9% increased Attack and Cast Speed"],
   :category {:accessories ["amulet"]},
   :requirements [{:name "Level", :values [["43" 0]], :displayMode 0}],
   :typeLine "Mandible Talisman",
   :flavourText
   ["The First Ones hold us\r"
    "between two sharpened blades.\r"
    "That should we stray too far from the path,\r"
    "we find ourselves severed.\r"
    "- The Wolven King"],
   :corrupted true,
   :frameType 2,
   :name "Phoenix Gorget",
   :w 1,
   :explicitMods
   ["+85 to maximum Life"
    "+44 to maximum Mana"
    "+13% to all Elemental Resistances"
    "+23% to Fire Resistance"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Amulets/NewTalisman.png?scale=1&w=1&h=1&v=ad5f0ea8fe142ed1e4c264661f29a8e6",
   :ilvl 74,
   :talismanTier 1,
   :h 1,
   :id "586068b7cc0ae312a799cfdf17f7bffe4e0455969e81b4adacae629cada07222",
   :craftedMods ["+21% to Cold Resistance"],
   :inventoryId "Stash3",
   :x 23,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const synthed-data
  "Rarity: Rare
Hate Ward
Synthesised Crypt Armour
--------
Quality: +4% (augmented)
Evasion Rating: 341 (augmented)
Energy Shield: 74 (augmented)
--------
Requirements:
Level: 56
Dex: 82
Int: 82
--------
Sockets: G B-G
--------
Item Level: 73
--------
Reflects 15 Physical Damage to Melee Attackers
--------
+40 to Intelligence
+20 to Evasion Rating
+11 to maximum Energy Shield
+38% to Fire Resistance
+36% to Cold Resistance
--------
Synthesised Item")
(def ^:const synthed-api
  {:y 0,
   :implicitMods ["Reflects 15 Physical Damage to Melee Attackers"],
   :properties
   [{:name "Quality", :values [["+4%" 1]], :displayMode 0, :type 6}
    {:name "Evasion Rating", :values [["341" 1]], :displayMode 0, :type 17}
    {:name "Energy Shield", :values [["74" 1]], :displayMode 0, :type 18}],
   :category {:armour ["chest"]},
   :requirements
   [{:name "Level", :values [["56" 0]], :displayMode 0}
    {:name "Dex", :values [["82" 0]], :displayMode 1}
    {:name "Int", :values [["82" 0]], :displayMode 1}],
   :typeLine "Synthesised Crypt Armour",
   :frameType 2,
   :name "Hate Ward",
   :w 2,
   :explicitMods
   ["+40 to Intelligence"
    "+20 to Evasion Rating"
    "+11 to maximum Energy Shield"
    "+38% to Fire Resistance"
    "+36% to Cold Resistance"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/BodyArmours/BodyDexInt4C.png?scale=1&w=2&h=3&synthesised=1&v=648a82d9c23d1c8e7546b8c3637bbb63",
   :ilvl 73,
   :sockets
   [{:group 0, :attr "D", :sColour "G"}
    {:group 1, :attr "I", :sColour "B"}
    {:group 1, :attr "D", :sColour "G"}],
   :socketedItems [],
   :h 3,
   :id "19940e6f85b04edaf809ccac8a305721f57606b7a2fb5a05a25e4dbd5e0dd4f2",
   :inventoryId "Stash36",
   :x 6,
   :identified true,
   :league "Synthesis",
   :verified false,
   :synthesised true})
(def ^:const shaped-data
  "Rarity: Rare
Hypnotic Nails
Assassin's Mitts
--------
Evasion Rating: 104
Energy Shield: 20
--------
Requirements:
Level: 58
Dex: 45
Int: 45
--------
Sockets: B-B-G-B
--------
Item Level: 79
--------
Socketed Gems are supported by Level 16 Blind
+19 to Intelligence
Adds 3 to 7 Physical Damage to Attacks
+165 to Accuracy Rating
6% Global chance to Blind Enemies on hit
--------
Shaper Item")
(def ^:const shaped-api
  {:y 13,
   :properties
   [{:name "Evasion Rating", :values [["104" 0]], :displayMode 0, :type 17}
    {:name "Energy Shield", :values [["20" 0]], :displayMode 0, :type 18}],
   :category {:armour ["gloves"]},
   :requirements
   [{:name "Level", :values [["58" 0]], :displayMode 0}
    {:name "Dex", :values [["45" 0]], :displayMode 1}
    {:name "Int", :values [["45" 0]], :displayMode 1}],
   :typeLine "Assassin's Mitts",
   :frameType 2,
   :name "Hypnotic Nails",
   :w 2,
   :explicitMods
   ["Socketed Gems are supported by Level 16 Blind"
    "+19 to Intelligence"
    "Adds 3 to 7 Physical Damage to Attacks"
    "+165 to Accuracy Rating"
    "6% Global chance to Blind Enemies on hit"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/Gloves/GlovesDexInt2.png?scale=1&w=2&h=2&v=8301a0119606f022ec99a9723749003d",
   :ilvl 79,
   :sockets
   [{:group 0, :attr "I", :sColour "B"}
    {:group 0, :attr "I", :sColour "B"}
    {:group 0, :attr "D", :sColour "G"}
    {:group 0, :attr "I", :sColour "B"}],
   :socketedItems [],
   :shaper true,
   :h 2,
   :id "72395aaea6ae4d111e451aca842148f526153943937edca9d8dfd7bbb38b15d5",
   :inventoryId "Stash2",
   :x 2,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const elder-data
  "Rarity: Rare
Rune Suit
Silk Robe
--------
Energy Shield: 65
--------
Requirements:
Level: 64
Int: 91
--------
Sockets: B
--------
Item Level: 81
--------
8% increased maximum Life
+73 to maximum Mana
+28% to Cold Resistance
+40% to Lightning Resistance
--------
Elder Item")
(def ^:const elder-api
  {:y 0,
   :properties
   [{:name "Energy Shield", :values [["65" 0]], :displayMode 0, :type 18}],
   :category {:armour ["chest"]},
   :requirements
   [{:name "Level", :values [["64" 0]], :displayMode 0}
    {:name "Int", :values [["91" 0]], :displayMode 1}],
   :typeLine "Silk Robe",
   :frameType 2,
   :name "Rune Suit",
   :w 2,
   :elder true,
   :explicitMods
   ["8% increased maximum Life"
    "+73 to maximum Mana"
    "+28% to Cold Resistance"
    "+40% to Lightning Resistance"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Armours/BodyArmours/BodyInt2C.png?scale=1&w=2&h=3&v=3230b59077d8340c5b49196cce65e956",
   :ilvl 81,
   :sockets [{:group 0, :attr "I", :sColour "B"}],
   :socketedItems [],
   :h 3,
   :id "f77de848b15b109d6ef2992f80aedaeb16fef6e6198b1cd7e8ad207450aff636",
   :inventoryId "Stash32",
   :x 5,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const div-arrogance-data
  "Rarity: Divination Card
Arrogance of the Vaal
--------
Stack Size: 4/8
--------
Item
 Two-Implicit
 Corrupted
--------
Discovery can lead to beauty, or it can lead to ruin.
--------
Shift click to unstack.")
(def ^:const div-arrogance-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["4/8" 0]], :displayMode 0}],
   :category {:cards []},
   :typeLine "Arrogance of the Vaal",
   :flavourText ["Discovery can lead to beauty, or it can lead to ruin."],
   :artFilename "ArroganceoftheVaal",
   :frameType 6,
   :name "",
   :w 1,
   :explicitMods
   ["<uniqueitem>{Item}\r\n <corrupted>{Two-Implicit}\r\n <corrupted>{Corrupted}"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Divination/InventoryIcon.png?scale=1&w=1&h=1&v=a8ae131b97fad3c64de0e6d9f250d743",
   :ilvl 0,
   :stackSize 4,
   :maxStackSize 8,
   :h 1,
   :id "ca1a7c967b224b00d6161d694e9e1d3e2bb1b0146138363f2d731192c2a83a0d",
   :inventoryId "Stash10",
   :x 241,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const div-crumble-data
  "Rarity: Divination Card
Destined to Crumble
--------
Stack Size: 1/5
--------
Body Armour
Item Level: 100
--------
\"Let us not forget the most important lesson the Vaal taught us:
Nothing is eternal.\"
- Siosa Foaga, Imperial Scholar")
(def ^:const div-crumble-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["1/5" 0]], :displayMode 0}],
   :category {:cards []},
   :typeLine "Destined to Crumble",
   :flavourText
   ["<size:30>{\"Let us not forget the most important lesson the Vaal taught us:\r"
    "Nothing is eternal.\"\r"
    "- Siosa Foaga, Imperial Scholar}"],
   :artFilename "DestinedtoCrumble",
   :frameType 6,
   :name "",
   :w 1,
   :explicitMods
   ["<rareitem>{Body Armour}\r\n<default>{Item Level:} <normal>{100}"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Divination/InventoryIcon.png?scale=1&w=1&h=1&v=a8ae131b97fad3c64de0e6d9f250d743",
   :ilvl 0,
   :stackSize 1,
   :maxStackSize 5,
   :h 1,
   :id "f0095bed0c65fc6dc58299ef3a79c83682923bd61d821fa1e3d18501390a5d43",
   :inventoryId "Stash10",
   :x 138,
   :identified true,
   :league "Synthesis",
   :verified false})
(def ^:const essence-data
  "Rarity: Currency
Essence of Hysteria
--------
Stack Size: 2/9
--------
Upgrades a normal item to rare or reforges a rare item, guaranteeing one property

Weapon: 10% chance to Cast Level 20 Fire Burst on Hit
Gloves: Socketed Gems deal 175 to 225 additional Fire Damage
Boots: Drops Burning Ground while moving, dealing 2500 Fire Damage per second
Body Armour: 10% increased Area of Effect
Helmet: Socketed Gems have 50% chance to Ignite
Shield: Adds 40 to 80 Fire Damage if you've Blocked Recently
Quiver: Gain 10% of Physical Damage as Extra Fire Damage
Amulet: 40% increased total Recovery per second from Life Leech
Ring: Gain 10% of Physical Damage as Extra Fire Damage
Belt: Damage Penetrates 5% Elemental Resistances during any Flask Effect
--------
Right click this item then left click a normal or rare item to apply it.
Shift click to unstack.")
(def ^:const essence-api
  {:y 0,
   :properties [{:name "Stack Size", :values [["2/9" 0]], :displayMode 0}],
   :category {:currency []},
   :typeLine "Essence of Hysteria",
   :frameType 5,
   :name "",
   :w 1,
   :explicitMods
   ["Upgrades a normal item to rare or reforges a rare item, guaranteeing one property"
    ""
    "Weapon: 10% chance to Cast Level 20 Fire Burst on Hit"
    "Gloves: Socketed Gems deal 175 to 225 additional Fire Damage"
    "Boots: Drops Burning Ground while moving, dealing 2500 Fire Damage per second"
    "Body Armour: 10% increased Area of Effect"
    "Helmet: Socketed Gems have 50% chance to Ignite"
    "Shield: Adds 40 to 80 Fire Damage if you've Blocked Recently"
    "Quiver: Gain 10% of Physical Damage as Extra Fire Damage"
    "Amulet: 40% increased total Recovery per second from Life Leech"
    "Ring: Gain 10% of Physical Damage as Extra Fire Damage"
    "Belt: Damage Penetrates 5% Elemental Resistances during any Flask Effect"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Currency/Essence/Terror1.png?scale=1&w=1&h=1&v=b6310bff75f61da7446462b7abdbb0b7",
   :ilvl 0,
   :stackSize 2,
   :maxStackSize 5000,
   :h 1,
   :id "ca7dba13219c261a0407147a81012c0902196f3b902d4494fee2594a9d153af3",
   :inventoryId "Stash9",
   :x 100,
   :identified true,
   :league "Synthesis",
   :descrText
   "Right click this item then left click a normal or rare item to apply it.",
   :verified false})
(def ^:const magic-cluster-data
  "Rarity: Magic
Cobalt Small Cluster Jewel of the Whelpling
--------
Requirements:
Level: 54
--------
Item Level: 77
--------
Adds 3 Passive Skills (enchant)
Added Small Passive Skills grant: +15% to Lightning Resistance (enchant)
--------
Added Small Passive Skills also grant: +2% to Fire Resistance
Added Small Passive Skills also grant: +6 to Maximum Mana
--------
Place into an allocated Small, Medium or Large Jewel Socket on the Passive Skill Tree. Added passives do not interact with jewel radiuses. Right click to remove from the Socket.")
(def ^:const magic-cluster-api
  {:y 0,
   :requirements [{:displayMode 0, :name "Level", :values [["54" 0]]}],
   :typeLine "Cobalt Small Cluster Jewel of the Whelpling",
   :enchantMods
   ["Adds 3 Passive Skills"
    "Added Small Passive Skills grant: +15% to Lightning Resistance"],
   :frameType 1,
   :name "",
   :w 1,
   :explicitMods
   ["Added Small Passive Skills also grant: +2% to Fire Resistance"
    "Added Small Passive Skills also grant: +6 to Maximum Mana"],
   :icon
   "https://web.poecdn.com/image/Art/2DItems/Jewels/NewGemBase1.png?w=1&h=1&scale=1&v=4f814e93a702f889fdac758b373c9e9d",
   :ilvl 77,
   :h 1,
   :id
   "3258110e924fb9e327c8426e139c1308e63c18aa0fbd302db13c5e5cc0b6addd",
   :inventoryId "Stash3",
   :context
   {:colour {:r 221, :g 221, :b 221},
    :type "QuadStash",
    :name "y",
    :index 2,
    :username "indigo747"},
   :x 2,
   :identified true,
   :league "Delirium",
   :descrText
   "Place into an allocated Small, Medium or Large Jewel Socket on the Passive Skill Tree. Added passives do not interact with jewel radiuses. Right click to remove from the Socket.",
   :verified false})
(i/item->enchant-block magic-cluster-api)

(def ^:cost item-cases
  "Map of item suite cases in the form apie -> data, or
   API map representation -> string representation, or
  input -> output."
  {maelstrom-star-api             maelstrom-star-data
   saphire-flask-api              saphire-flask-data
   fecund-vanguard-belt-api       fecund-vanguard-belt-data
   laviangas-wisdom-api           laviangas-wisdom-data
   item-rarity-gem-api            item-rarity-gem-data
   corrupted-ring-api             corrupted-ring-data
   vaal-blight-api                vaal-blight-data
   fractured-armor-api            fractured-armor-data
   corrupted-fractured-weapon-api corrupted-fractured-weapon-data
   unided-weapon-api              unided-weapon-data
   prophecy-api                   prophecy-data
   unset-ring-api                 unset-ring-data
   exalted-orb-api                exalted-orb-data
   jewellers-big-stack-api        jewellers-big-stack-data
   currency-shard-api             currency-shard-data
   lightpoacher-api               lightpoacher-data
   tombfist-api                   tombfist-data
   darkness-enthroned-api         darkness-enthroned-data
   atziris-mirror-api             atziris-mirror-data
   witchfire-api                  witchfire-data
   qual-white-map-api             qual-white-map-data
   corrupted-unid-map-api         corrupted-unid-map-data
   corrupted-map-api              corrupted-map-data
   rare-map-api                   rare-map-data
   whakawairua-tuahu-api          whakawairua-tuahu-data
   quality-vaal-gem-api           quality-vaal-gem-data
   abyss-jewel-api                abyss-jewel-data
   simple-frag-api                simple-frag-data
   divine-vessel-api              divine-vessel-data
   talisman-api                   talisman-data
   synthed-api                    synthed-data
   shaped-api                     shaped-data
   elder-api                      elder-data
   div-arrogance-api              div-arrogance-data
   div-crumble-api                div-crumble-data
   essence-api                    essence-data
   magic-cluster-api              magic-cluster-data
   })

(t/deftest test-item-cases
  (doseq [[item data] item-cases]
    (t/testing (i/full-item-name item)
      (t/is (= data (i/item->str item))))))

(t/deftest fixing-stash-index
  (t/testing "happy"
    (t/is (= 0 (i/stash-index {:inventoryId "Stash1"}))))

  (t/testing "full"
    (t/is (= 10 (i/stash-index conq-potency)))
    (t/is (= nil (i/stash-index worn-flask)))))

(t/deftest rarity-test
  (t/is (= :unique (i/rarity conq-potency)))
  (t/is (= :rare (i/rarity maelstrom-star)))
  #_(t/testing "rarity"))

(t/deftest frametype->str-test
  (t/testing "all"
    (t/is (= "Rare" (i/frametype->str 2)))))

(t/deftest properties
  (t/testing "flasks"
    (t/is (= "Consumes 30 of 60 Charges on use"
           (i/property->mod {:name "Consumes %0 of %1 Charges on use",
                           :values [["30" 0] ["60" 0]],
                           :displayMode 3})))
    (t/is (= "Lasts 4.00 Seconds" (i/property->mod {:name "Lasts %0 Seconds",
                                                :values [["4.00" 0]],
                                                :displayMode 3}))))

  (t/testing "weapon mods"
    (t/is (= "One Handed Mace"
           (i/property->mod {:name "One Handed Mace", :values [], :displayMode 0})))
    (t/is (= "Physical Damage: 32-74 (augmented)"
           (i/property->mod {:name "Physical Damage", :values [["32-74" 1]], :displayMode 0, :type 9})))
    (t/is (= "Critical Strike Chance: 5.00%"
           (i/property->mod {:name "Critical Strike Chance", :values [["5.00%" 0]], :displayMode 0, :type 12})))))

(t/deftest experience-reformat
  (t/testing "Experience number reformatting"
    (t/is (= "1/285,815"
           (i/reformat-experience "1/285815")))

    (t/is (= "11,111,111/2,222,222" (i/reformat-experience "11111111/2222222")))))

(t/deftest price-strings
  (t/testing "price->str"
    (t/is (= "~b/o 1 exa" (i/price->str [:bo 1 :exa])))
    (t/is (= "~price 3 alt" (i/price->str [:price 3 :alt])))
    (t/is (= #?(:clj "~price 10/3 chrom"
                :cljs "~price 3.33 chrom")
             (i/price->str [:price #?(:clj 10/3 :cljs 3.33) :chrom])))
    (t/is (thrown? #?(:clj java.lang.Exception
                      :cljs js/Error)
                   (s/check-asserts true)
                   (i/price->str [:asdf #?(:clj 10/3 :cljs 3.33) :alt]))))

  (t/testing "str->price"
    (t/is (= [:bo 1 :exa] (i/str->price "~b/o 1 exa")))
    (t/is (= [:price 3 :alt] (i/str->price "~price 3 alt")))
    (t/is (= [:price #?(:clj 10/3 :cljs 3.33) :chrom] (i/str->price
                                                       #?(:clj "~price 10/3 chrom"
                                                          :cljs "~price 3.33 chrom"))))
    (t/is (= nil (i/str->price "not a price")))))

(t/deftest whisper-text-test
  (t/testing "whisper text"
    (t/is (=
         "@LabbJugg Hi, I would like to buy your Dragon Bane Dragoon Sword listed for 10 chaos in Synthesis (stash tab \"frac1\"; position: left 8, top 1)"
         (i/make-whisper "LabbJugg" [:bo 10 :chaos] "frac1" corrupted-fractured-weapon-api)))
    (t/is (=
         "@LabbJugg Hi, I would like to buy your Jewelled Foil listed for 1 chaos in Synthesis (stash tab \"44\"; position: left 4, top 7)"
         (i/make-whisper "LabbJugg" [:bo 1 :chaos] "44" unided-weapon-api)))))

(t/deftest item-quality-test
  (t/testing "With quality"
    (t/is (= 5 (i/item-quality atziris-mirror-api))))
  (t/testing "Without quality"
    (t/is (= 0 (i/item-quality darkness-enthroned-api)))))
