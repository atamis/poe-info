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
            [poe-info.api :as api])
  (:gen-class))

(comment

  (doseq [i [1 2 3]]
    (println i)
    (let [filename (format "archive/tab%d-%s.json" i (str (jtime/local-date-time)))]
      (->> (client/get (api/stash-item-url username i) {:cookie-store cs})
           :body
           (spit filename)))))

(defn archive-tabs
  [username cs]
  (let [sanitize #(-> %
                      (clojure.string/replace #" " "_")
                      (clojure.string/replace #"[^a-zA-Z0-9\-\_]" ""))
        tabs (->> (client/get (api/stash-item-url username)
                              {:as :json :cookie-store cs})
                  :body
                  :tabs
                  (map #(-> %
                            (select-keys [:n :i])
                            (clojure.set/rename-keys {:n :name :i :index})
                            (update :name sanitize))))]
    (doseq [{:keys [name index]} tabs]
      (let [filename (format "cache/archive_%d_%s.json" index name)]
        (clojure.java.io/make-parents filename) ; really only 1 is necessary
        (as-> (client/get (api/stash-item-url username index) {:cookie-store cs}) $
          (:body $)
          (spit filename $))
        (println filename)))))

(defn histogram-identified
  "Histogram of whether items are identified or not."
  [items]
  (->> items (map item/identified?) util/histogram))

(defn percent-full
  "Calculates the percentage of slots full in dump tabs."
  [stashes items]
  (float (/ (->> items (map item/size) (reduce +))
            (* (count stashes) constants/quad-stash))))

(defn- count-div-cards
  "Counts the number of divination cards"
  [items]
  (->> items (filter #(= (:category %) {:cards []})) count))

(defn- histogram-identified-rares
  "Histogram of rares and whether they are identified or not."
  [items]
  (->> items (filter #(= (item/rarity %) :rare))
       (map :identified)
       util/histogram))

(defn -main
  "Count how many items are in tab indexes 1, 2, 3. Loads data from config.edn."
  [& args]

  (def cs (api/make-cs (config/config :poesessid)))
  (def username (config/config :username))

  (def stashes [1 2 3])

  (def items
    (->>
     stashes
     (map #(api/stash-item-url username %))
     (map #(client/get % {:cookie-store cs :as :json}))
     (mapcat #(get-in % [:body :items]))))

  (clojure.pprint/pprint
   {:count (count items)
    :identified (histogram-identified items)
    :percent-full (percent-full stashes items)
    :div-cards (count-div-cards items)
    :identified-rares (histogram-identified-rares items)}))

(comment
  (->> items (filter #(= (:category %) {:currency []})) (map :typeLine) util/histogram))
