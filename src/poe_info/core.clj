(ns poe-info.core
  (:require [clj-http.client :as client]
            [clj-http.cookies :as cookies]
            [clojure.data.json :as json]
            [clojure.string :as string]
            [java-time :as jtime]

            [poe-info.util :as util]
            [poe-info.item :as item]
            [poe-info.constants :as constants]
            [poe-info.gems :as gems]
            [poe-info.config :as config]
            )
  (:gen-class))

(defn enumerate [s]
  (map vector (range) s))

;; Frametype
;; 0: normal
;; 1: magic
;; 2: rare
;; 3: unique
;; 4: gem

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
  (rarity->str (item/frametype->rarity ft)))

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
            (enumerate values))
    (if (empty? values)
      name
      (let [[value aug?] (first values)]
        (str name ": " (if (= name "Experience") (reformat-experience value) value) (when (= aug? 1) " (augmented)"))))))

(defn requirement->str
  "Converts a requirement description to a string for an item block."
  [{name :name [[value _]] :values}]
  (str name ": " value))

(defn item->str
  "Converts an item description from the API to an item block like the game generates."
  [item]
  ; blocks, interpose separators, flatten, and filter.
  (->>
   [[(str "Rarity: " (frametype->str (:frameType item)))

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

    [(:explicitMods item)]

    [(:descrText item)]

    [(when (:flavourText item) (map string/trim (:flavourText item)))]

    [(when (:corrupted item) "Corrupted")]]
   (map #(filter (complement nil?) %))
   (filter (complement empty?))
   (interpose item-str-sep)
   flatten
   ;(map empty->nil)
   (filter (complement nil?))
   (string/join "\n")))

(defn get-stash-item-url
  "Get the URL for the given username and tab index (default 0)."
  ([username] (get-stash-item-url username 0))
  ([username index]
   (str "https://www.pathofexile.com/character-window/get-stash-items?league=Synthesis&tabs=1&tabIndex=" index "&accountName=" username)))

(defn my-account-url
  "THe URL for the my-account page."
  []
  "https://www.pathofexile.com/my-account")

(defn find-sess-cookie
  "Return the cookie object that has the name POESESSID."
  [cs]
  (first (filter #(= (.getName %) "POESESSID") (.getCookies cs))))

(defn make-cs
  "Make a cookie store with the POESESSID cookie set to the given id."
  [id]
  (def cs (clj-http.cookies/cookie-store))

  (client/get (my-account-url) {:cookie-store cs})

  (def sess-cookie (find-sess-cookie cs))

  (.setValue sess-cookie id)

  cs)

(comment

  (doseq [i [1 2 3]]
    (println i)
    (let [filename (format "archive/tab%d-%s.json" i (str (jtime/local-date-time)))]
      (->> (client/get (get-stash-item-url username i) {:cookie-store cs})
           :body
           (spit filename)))))

(defn -main
  "Count how many items are in tab indexes 1, 2, 3. Loads data from ./poesessid and ./username."
  [& args]

  (def cs (make-cs (config/config :poesessid)))
  (def username (config/config :username))

  (def stashes [1 2 3])

  (def items
    (->>
     stashes
     (mapcat #(get-in (client/get (get-stash-item-url username %) {:cookie-store cs :as :json}) [:body :items]))))

  (clojure.pprint/pprint
   {:count (count items)
    :identified (->> items (map item/identified?) util/histogram)
    :percent-full (float (/ (->> items (map item/size) (reduce +))
                            (* (count stashes) constants/quad-stash)))
    :div-cards (->> items (filter #(= (:category %) {:cards []})) count)
    :rares (->> items (filter #(= (item/rarity %) :rare))
                (map :identified)
                util/histogram)})
  ;(prn cs)
  )

(comment
  (->> items (filter #(= (:category %) {:currency []})) (map :typeLine) util/histogram))
