(ns poe-info.item
  (:require
   [clojure.string :as string]
   [#?(:clj clojure.spec.alpha
       :cljs cljs.spec.alpha) :as s]
   [#?(:clj clojure.edn
       :cljs cljs.reader) :as reader]

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
   4 :gem
   5 :currency
   6 :divination
   8 :prophecy})

(defn stash-index
  "If the item is in a stash tab, returns in the integer index for that stash.
  Returns nil otherwise (like if the item was equipped on a character.)"
  [{:keys [inventoryId]}]
  (let [[_ s] (re-find #"Stash(.+)"  inventoryId)]
    (when s
      (dec (util/parse-int s)))))

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
   :gem "Gem"
   :currency "Currency"
   :divination "Divination Card"
   :prophecy "Normal" ;; Lol
   })

(defn frametype->str
  "Combines frametype->rarity and rarity->str"
  [ft]
  (rarity->str (frametype->rarity ft)))

(defn item-quality
  "Find the quality of the item, or nil if the item has no quality."
  [item]
  (if-let [qual-prop (->> item
                          :properties
                          (filter #(= (:name %) "Quality"))
                          first)]
    (as-> qual-prop $
      (:values $)
      (first $)
      (first $)
      (re-find #"\+(.+)%" $)
      (second $)
      (util/parse-int $))
    0))

(def item-str-sep "Used to separate blocks in item descriptions" "--------")

(defn empty->nil
  "If it's an empty string, return nil. Otherwise, return unchanged."
  [s]
  (if (= s "")
    nil
    s))

(defn full-item-name
  "Concatenate the name and base to make the item's full name."
  [{:keys [typeLine name]}]
  (string/trim (string/join " " (filter some? [name typeLine]))))

(s/def ::block (s/every string?))
(s/def ::blocks (s/every ::block))

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
  (let [[cur total :as nums] (->> (string/split s #"/")
                         (map util/parse-int))]
    #?(:clj (util/fmt "%,d/%,d" cur total)
       :cljs (apply util/fmt "%s/%s"
                    (map #(.toLocaleString % "en") nums)))))

(defn property-special-case
  [name value]
  (case name
    "Experience" (reformat-experience value)
    "Stack Size" (reformat-experience value)
    value))

(defn property->mod
  "Convert a property to a mod string. Deals specially with value replacement, value-less properties, and experience reformatting."
  [{:keys [name values] :as property}]
  (let [add-aug (fn [value]
                  (let [[value aug?] value]
                    (str
                     (property-special-case name value)
                     (when (< 0 aug?) " (augmented)"))))]
    (if (string/includes? name "%")
      (reduce (fn [s [idx [val _ :as sub-prop]]]
                (string/replace s (str "%" idx) (add-aug sub-prop)))
              name
              (util/enumerate values))
      (if (empty? values)
        name
        (str name ": "
             (->> values
                  (map add-aug)
                  (clojure.string/join ", ")))))))

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

(defn add-crafted
  [mods]
  (map #(str % " (crafted)") mods))

(defn concat-blocks
  [blocks new-blocks]
  (into [] (concat blocks new-blocks)))

(defn unided?
  [item]
  ;; Gems are identified, but don't have the :identified field,
  ;; so nil doesn't mean unidentified.
  (= false (:identified item)))

(defn unstackable?
  [item]
  (and (:stackSize item) (< 1 (:stackSize item))))

(defn item-ilvl?
  [item]
  (and (:ilvl item) (not= (:ilvl item) 0)))

(defn div-card-tag-handler
  [_tag text]
  text)

;; This gets called on explicit mods individually, and on
;; concatenated flavour text because for some god awful reason,
;; flavour text's tags can span newlines.


(defn text-tag-handler
  [mod]
  (-> mod
      (string/replace "\r" "")
      (string/replace
       #"<([^\>]+)>\{([^\}]+)\}"
       #(apply div-card-tag-handler (rest %)))))

(defn flavour-text-handler
  [flavour-text]
  (->> flavour-text
       (string/join "\n")
       text-tag-handler))

(defn item->header-block
  [item]
  {:post [(s/valid? ::block %)]}
  (cond-> []
    (:frameType item) (conj (str "Rarity: " (frametype->str (:frameType item))))
    (and (:name item) (not= "" (:name item))) (conj (:name item))
    (some? (:typeLine item)) (conj (:typeLine item))))

(defn item->properties-block
  [item]
  {:post [(s/valid? ::block %)]}
  (as-> (:properties item) $
    (concat $ (:additionalProperties item))
    (map property->mod $)
    (into [] $)
    (concat $ (:utilityMods item))))

(defn item->requirements-block
  [item]
  {:post [(s/valid? ::block %)]}
  (apply vector
         "Requirements:"
         (map requirement->str (:requirements item))))

(defn item->explicit-block
  [item]
  {:post [(s/valid? ::block %)]}
  (concat
   (when (:fractured item) (add-fractured (:fracturedMods item)))
   (map text-tag-handler (:explicitMods item))
   (when-let [mods (:craftedMods item)] (add-crafted mods))))

(defn item->descr-text-block
  [item]
  {:post [(s/valid? ::block %)]}
  (concat
   [(:descrText item)]
   (when (unstackable? item) ["Shift click to unstack."])))

(defn prophecy->blocks
  [item]
  {:post [(s/valid? ::blocks %)]}
  [(item->header-block item)
   (:flavourText item)
   [(:prophecyText item)]
   [(:descrText item)]])

(defn item->blocks
  [item]
  {:post [(s/valid? ::blocks %)]}
  (cond-> []
    true                 (conj (item->header-block item))
    (:properties item)   (conj (item->properties-block item))
    (:requirements item) (conj (item->requirements-block item))
    (:secDescrText item) (conj [(:secDescrText item)])
    (:sockets item)      (conj [(str "Sockets: " (sockets->str (:sockets item)))])
    (item-ilvl? item)    (conj [(str "Item Level: " (:ilvl item))])
    (:talismanTier item) (conj [(str "Talisman Tier: " (:talismanTier item))])
    (:implicitMods item) (conj (:implicitMods item))
    (:explicitMods item) (conj (item->explicit-block item))
    (:vaal item)         (concat-blocks (item->blocks (:vaal item)))
    (:flavourText item)  (conj [(flavour-text-handler (:flavourText item))])
    (unided? item)       (conj ["Unidentified"])
    (:descrText item)    (conj (item->descr-text-block item))
    (:prophecyText item) (conj [(:prophecyText item)])
    (:corrupted item)    (conj ["Corrupted"])
    (:fractured item)    (conj ["Fractured Item"])
    ;; This might be somehwere else.
    (:synthesised item) (conj ["Synthesised Item"])
    (:shaper item) (conj ["Shaper Item"])
    (:elder item) (conj ["Elder Item"])
    ;; For some reason, divination cards have their unshift
    ;; message in a separate block, while currency doesn't.
    (and (= (rarity item)
            :divination)
         (unstackable? item)) (conj ["Shift click to unstack."])))

(defn item-block-dispatch
  [item]
  {:post [(s/valid? ::blocks %)]}
  (case (rarity item)
    :prophecy (prophecy->blocks item)
    (item->blocks item)))

;; TODO Fix this.
(defn item->str
  "Converts an item description from the API to an item block like the game generates."
  [item]
  ; blocks, interpose separators, flatten, concat lines.
  (let [item (if (:vaal item) (vaal-manip item) item)]
    (->>
     (item-block-dispatch item)
     (interpose item-str-sep)
     flatten
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

(defn str->currency
  [s]
  (if (contains? currency-strings s)
    (keyword s)
    s))

(s/def ::price (s/tuple #{:bo :price} number? currencies))

(defn price->str
  "Convert a price in the form []"
  [[policy amt currency :as price]]
  (s/assert ::price price)
  (let [policy-str {:bo "b/o" :price "price"}]
    (util/fmt "~%s %s %s" (policy-str policy) (str amt) (name currency))))

(defn str->price
  [s]
  (if-let [[_ policy-s amt-s currency-s] (re-find #"~(b/o|price) (.+) (.+)" s)]
    (let [policy ({"b/o" :bo "price" :price} policy-s)
          amt (reader/read-string amt-s)
          currency (str->currency currency-s)]
      [policy amt currency])
    nil))

(defn human-readable-price
  [[_ amt currency :as price]]
  (s/assert ::price price)
  (str amt " " (name currency)))

(defn make-whisper
  [username
   price
   stash
   {:keys [league x y] :as item}]
  (let [left (inc x)
        top (inc y)
        price (if (s/valid? ::price price) (human-readable-price price) price)
        item-name (full-item-name item)]
    (util/fmt "@%s Hi, I would like to buy your %s listed for %s in %s (stash tab \"%s\"; position: left %d, top %d)" username item-name price league stash left top)))

(defn item-price
  [{{:keys [name]}:context :keys [note]}]
  (if note
    (str->price note)
    (when name
      (str->price name))))

(defn price-difference
  [& prices]
  (let [amounts (map second prices)
        currencies (map #(nth % 2) prices)]
    (when (apply = currencies)
      (apply - amounts))))

;; Experimentally determined type ranges


(def category-range
  ""
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

(def ring-typeline-range
  ["Moonstone Ring"
   "Two-Stone Ring"
   "Sapphire Ring"
   "Paua Ring"
   "Topaz Ring"
   "Amethyst Ring"
   "Coral Ring"
   "Prismatic Ring"
   "Diamond Ring"
   "Iron Ring"
   "Unset Ring"
   "Gold Ring"
   "Ruby Ring"
   "Steel Ring"])

;; Note: :note is only when set explicitly. Implicit notes are
;; not included.

(def item-key-range
  "All the item keys from a large (although not exhaustive) sample of items."
  [:abyssJewel
   :additionalProperties
   :artFilename
   :category
   :corrupted
   :craftedMods
   :delve
   :descrText
   :duplicated
   :elder
   :enchantMods
   :explicitMods
   :flavourText
   :flavourTextParsed
   :fractured
   :fracturedMods
   :frameType
   :h
   :icon
   :id
   :identified
   :ilvl
   :implicitMods
   :inventoryId
   :league
   :maxStackSize
   :name
   :note
   :properties
   :prophecyText
   :requirements
   :secDescrText
   :shaper
   :socketedItems
   :sockets
   :stackSize
   :support
   :synthesised
   :talismanTier
   :typeLine
   :utilityMods
   :vaal
   :veiled
   :veiledMods
   :verified
   :w
   :x
   :y])
