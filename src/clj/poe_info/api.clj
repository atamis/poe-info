(ns poe-info.api
  (:require [clj-http.client :as client]
            [clj-http.cookies :as cookies]
            [org.bovinegenius.exploding-fish :as uri]

            [poe-info.item :as item]))

(defn stash-items-context
  "Takes the body of a stash tab API call, adds context to each item, and returns the items"
  [username response-body]
  (let [stash-index (-> response-body
                        :items
                        first
                        item/stash-index)
        context (-> response-body
                    :tabs
                    (nth stash-index)
                    (select-keys [:n :colour :i :type])
                    (clojure.set/rename-keys {:n :name :i :index})
                    (assoc :username username)
                    )]

    (map #(assoc % :context context) (:items response-body))))

;; Private API


(defn stash-item-url
  "Get the URL for the given username and tab index (default 0)."
  ([username] (stash-item-url username 0))
  ([username index]
   (-> "https://www.pathofexile.com/character-window/get-stash-items"
       uri/uri
       (uri/query-map {:league "Synthesis"
                       :tabIndex index
                       :accountName username
                       :tabs 1})
       str)))

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

(defn get-character-items
  "cs is a cookie store, params is a map with the \"accountName\", \"realm\", and \"character\". This triggers a post request."
  [cs params & {:as opts}]
  (let [default {:form-params (select-keys params [:accountName, :realm, :character])
                 :cookie-store cs
                 :as :json}
        options (merge default opts)]
    (client/post "https://www.pathofexile.com/character-window/get-items"
                 options)))

(defn get-characters
  [cs params & {:as opts}]
  (let [default {:form-params (select-keys params [:accountName :realm])
                 :cookie-store cs
                 :as :json}
        options (merge default opts)]
    (client/post "https://www.pathofexile.com/character-window/get-characters"
                 options)))

;; Public API

(defn leagues-url
  "Get a list of current and past leagues."
  [& {:as opts}]
  (-> "http://api.pathofexile.com/leagues"
      uri/uri
      (uri/query-map (select-keys opts [:type :realm :season :compact :limit :offset]))
      str))

(defn league-url
  "Get a single league by id."
  [id & {:as opts}]
  (let [params (select-keys opts
                            [:realm :ladder :ladderLimit :ladderOffset :ladderTrack])
        params (if (:ladder params) (update params :ladder {false 0 true 1}))]
    (-> (str "http://api.pathofexile.com/leagues/" id)
        uri/uri
        (uri/query-map params)
        str)))

(defn public-stash-tabs-url
  ([] (public-stash-tabs-url nil))
  ([id]
   (-> (uri/uri "http://api.pathofexile.com/public-stash-tabs")
       (uri/query-map (if id {:id id} {}))
       (str))))

(defn league-rules-url
  "Get a list of all possible league rules."
  []
  "http://api.pathofexile.com/league-rules")

(defn league-rule-url
  "Get a single league rule by id."
  [id]
  (str "http://api.pathofexile.com/league-rules/" id))

;; poeprices.info

(defn b64-encode
  "Base64 encodes a string, returning a string."
  [s]
  (.encodeToString (java.util.Base64/getEncoder) (.getBytes s)))

(defn poeprices-prediction
  [league item-data & {:as opts}]
  (let [defaults {:form-params
                  {:l league
                   :i (b64-encode item-data)}
                  :as :json}
        opts (merge defaults opts)]
    (if (:async? opts)
      (client/post "https://poeprices.info/api"
                   opts
                   (:respond-callback opts)
                   (:raise-callback opts)
                   )
      (client/post "https://poeprices.info/api"
                   opts))))
