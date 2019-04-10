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
