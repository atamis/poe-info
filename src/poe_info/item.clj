(ns poe-info.item
  (:require
   [clojure.string :as string]
   [clojure.spec.alpha :as s]

   [poe-info.util :as util]))

(def class-id->name
  "Convert a classId to a symbol for the base class."
  {0 :scion
   1 :maurader
   2 :ranger
   3 :witch
   4 :duelist
   5 :templar
   6 :shadow})

;; TODO: incomplete mapping.
(def ascendency->name
  "Convert an [classId ascendencyClass] and to a symbol representing the ascendency class."
  {[0 1] :ascendant
   [1 1] :juggernaut
   [2 3] :pathfinder
   [3 3] :necromancer
   [3 1] :occultist
   [4 3] :champion
   [5 2] :hierophant
   [6 3] :saboteur
   [6 2] :trickster
   [6 0] :shadow})

;; Frametype
;; 0: normal
;; 1: magic
;; 2: rare
;; 3: unique
;; 4: gem


(def frametype->rarity
  "Convert a frameType to a symbol representing the rarity (including gem)"
  {0 :normal
   1 :magic
   2 :rare
   3 :unique
   4 :gem})

(defn stash-index
  "If the item is in a stash tab, returns in the integer index for that stash.
  Returns nil otherwise (like if the item was equipped on a character.)"
  [{:keys [inventoryId]}]
  (let [[_ s] (re-find #"Stash(.+)"  inventoryId)]
    (when s
      (dec (Integer/parseInt s)))))

(defn rarity
  [{:keys [frameType]}]
  (frametype->rarity frameType))

(def identified? :identified)

(defn size
  [{:keys [w h]}]
  (* w h))

(def rarity->str
  "Convert a rarity symbol to a capitalized string"
  {:normal "Normal"
   :magic "Magic"
   :rare "Rare"
   :unique "Unique"
   :gem "Gem"})

(defn frametype->str
  "Combines frametype->rarity and rarity->str"
  [ft]
  (rarity->str (frametype->rarity ft)))

(def item-str-sep "Used to separate blocks in item descriptions" "--------")

(defn empty->nil
  "If it's an empty string, return nil. Otherwise, return unchanged."
  [s]
  (if (= s "")
    nil
    s))

;; TODO: check abyss sockets.
(defn sockets->str
  "Convert a complex socket description from the item API into the simplified description in item blocks."
  [sockets]
  (->> sockets
       (group-by :group)
       (map second)
       (map #(map (comp string/upper-case :sColour) %))
       (map #(interpose "-" %))
       (interpose " ")
       flatten
       string/join))

(defn reformat-experience
  "Take a string representing gem experience and add commas to separate number groups."
  ;; This is necessary because the game gives experience numbers with commas, and the API doesn't have commas.
  [s]
  (->> (string/split s #"/")
       (map #(Integer/parseInt %))
       (apply format "%,d/%,d")))

(defn property->mod
  "Convert a property to a mod string. Deals specially with value replacement, value-less properties, and experience reformatting."
  [{name :name values :values :as property}]
  (if (string/includes? name "%")
    (reduce (fn [s [idx [val _]]]
              (string/replace s (str "%" idx) val))
            name
            (util/enumerate values))
    (if (empty? values)
      name
      (str name ": "
           (->> values
                (map (fn [value]
                       (let [[value aug?] value]
                         (str
                          (if (= name "Experience")
                            (reformat-experience value)
                            value)
                          (when (< 0 aug?) " (augmented)")))))
                (clojure.string/join ", "))))))

(defn requirement->str
  "Converts a requirement description to a string for an item block."
  [{name :name [[value _]] :values}]
  (str name ": " value))

(defn vaal-manip
  [item]
  (let [vaal-name (:typeLine item)
        base-name (-> item :vaal :baseTypeName)]
    (-> item
        (assoc :typeLine base-name)
        (assoc-in [:vaal :typeLine] vaal-name))))

(defn add-fractured
  [mods]
  (map #(str % " (fractured)") mods))

;; TODO Fix this.
(defn item->str
  "Converts an item description from the API to an item block like the game generates."
  [item]
  ; blocks, interpose separators, flatten, and filter.
  (let [item (if (:vaal item) (vaal-manip item) item)]
    (->>
     [[(when (:frameType item) (str "Rarity: " (frametype->str (:frameType item))))

       (empty->nil (:name item))
       (:typeLine item)]

      [(when (:properties item)
         [(map property->mod (:properties item))
          (when (:additionalProperties item) (map property->mod (:additionalProperties item)))])

       (:utilityMods item)]

      [(when (:requirements item)
         ["Requirements:"
          (map requirement->str (:requirements item))])]

      [(:secDescrText item)]

      [(when (:sockets item)
         (str "Sockets: " (sockets->str (:sockets item))))]

      [(when (and (:ilvl item) (not= (:ilvl item) 0))  (str "Item Level: " (:ilvl item)))]

      [(:implicitMods item)]

      [(when (:fractured item) (add-fractured (:fracturedMods item)))
       (:explicitMods item)]

      [(when (:vaal item) (item->str (:vaal item)))]

      [(:descrText item)]

      [(when (:flavourText item) (map string/trim (:flavourText item)))]

      ;; Gems do not have an :identified tag, but are always identified
      [(when (= false (:identified item)) "Unidentified")]

      [(when (:corrupted item) "Corrupted")]

      [(when (:fractured item) "Fractured Item")]]

     (map #(filter (complement nil?) %))
     (filter (complement empty?))
     (interpose item-str-sep)
     flatten
                                        ;(map empty->nil)
     (filter (complement nil?))
     (string/join "\n"))))

(def currencies #{:chrom
                  :alt
                  :jewel
                  :chance
                  :chisel :cartographer
                  :fusing :fuse
                  :alch
                  :scour
                  :blessed
                  :chaos
                  :regret
                  :regal
                  :gcp :gemcutter
                  :divine
                  :exalted :exa
                  :mirror
                  :perandus
                  :silver})

(def ^:no-doc currency-strings (->> currencies (map name) (into #{})))

(s/def ::price (s/tuple #{:bo :price} number? currencies))

(defn price->str
  "Convert a price in the form []"
  [[policy amt currency :as price]]
  (s/assert ::price price)
  (let [policy-str {:bo "b/o" :price "price"}]
    (format "~%s %s %s" (policy-str policy) (str amt) (name currency))))

(defn str->price
  [s]
  (if-let [[_ policy-s amt-s currency-s] (re-find #"~(b/o|price) (.+) (.+)" s)]
    (let [policy ({"b/o" :bo "price" :price} policy-s)
          amt (clojure.edn/read-string amt-s)
          currency (if (contains? currency-strings currency-s)
                     (keyword currency-s)
                     currency-s)]
      [policy amt currency])
    nil))

(defn human-readable-price
  [[_ amt currency :as price]]
  (s/assert ::price price)
  (str amt " " (name currency)))

(defn make-whisper
  [username price stash {item-name :name :keys [typeLine league x y]}]
  (let [left (inc x)
        top (inc y)
        price (if (s/valid? ::price price) (human-readable-price price) price)
        item-name (string/trim (string/join " " (filter some? [item-name typeLine])))]
    (format "@%s Hi, I would like to buy your %s listed for %s in %s (stash tab \"%s\"; position: left %d, top %d)" username item-name price league stash left top)))


;; Experimentally determined type ranges


(def category-ranges
  [{:accessories ["amulet"]}
   {:accessories ["belt"]}
   {:accessories ["ring"]}
   {:armour ["boots"]}
   {:armour ["chest"]}
   {:armour ["gloves"]}
   {:armour ["helmet"]}
   {:armour ["quiver"]}
   {:armour ["shield"]}
   {:cards []}
   {:currency []}
   {:currency ["fossil"]}
   {:currency ["resonator"]}
   {:flasks []}
   {:gems ["activegem"]}
   {:gems ["supportgem"]}
   {:jewels []}
   {:jewels ["abyss"]}
   {:maps []}
   {:maps ["fragment"]}
   {:maps ["fragment" "scarab"]}
   {:weapons ["bow"]}
   {:weapons ["claw"]}
   {:weapons ["dagger"]}
   {:weapons ["oneaxe"]}
   {:weapons ["onemace"]}
   {:weapons ["onesword"]}
   {:weapons ["staff"]}
   {:weapons ["twoaxe"]}
   {:weapons ["twomace"]}
   {:weapons ["twosword"]}
   {:weapons ["wand"]}
   {:weapons ["sceptre" "onemace"]}])
