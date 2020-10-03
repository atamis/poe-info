(ns poe-info.api
  (:require [clj-http.client :as client]
            [clj-http.cookies :as cookies]
            [poe-info.util :as util]
            [org.bovinegenius.exploding-fish :as uri]
            [poe-info.item :as item]
            [org.httpkit.client :as http]
            [manifold.deferred :as d]
            [jsonista.core :as json]))

(defn stash-tab-add-context
  "Takes the body of a stash tab API call, adds context to each item, and returns the items"
  [{:keys [username]} index response-body]
  (let [stash-index (or (some-> response-body
                                :items
                                first
                                item/stash-index)
                        index)
        context (-> response-body
                    :tabs
                    (nth stash-index)
                    (select-keys [:n :colour :i :type])
                    (clojure.set/rename-keys {:n :name :i :index})
                    (assoc :username username))]
    (update response-body
            :items
            (partial map #(assoc % :context context)))))

;; Private API

;; TODO Realm should be #{pc xbox sony}

(def ^:private mapper
  (json/object-mapper
    {:encode-key-fn name
     :decode-key-fn keyword}))

(defn make-client
  "Make a client for a username, league, and session ID."
  [username league session-id]
  {:username username
   :league league
   :session-id session-id})

(defn request
  "Make an http-kit request in the context of the client"
  [{:keys [session-id] :as _client} r]
  (d/chain (http/request
            (util/deep-merge
             {:headers {"Cookie" (str "POESESSID=" session-id)
                        "Accept" "application/json"}
              :method :get
              :as :stream}
             r))
           #(update % :body json/read-value mapper)))

(defn stash-tab
  "Get the contents of a stash tab (including private tabs) based on the
  client's username, league, and tab index."
  [{:keys [username league] :as client} i]
  (d/chain (request
            client
            {:url "https://www.pathofexile.com/character-window/get-stash-items"
             :query-params {"league" league
                            "tabIndex" i
                            "accountName" username
                            "tabs" 1}})
           #(update % :body (partial stash-tab-add-context client i))))

(defn character-items
  "Get a character's items based on the client's username, character name, and
  realm."
  ([client character]
   (character-items client character "pc"))
  ([{:keys [username] :as client} character realm]
   (request
    client
    {:url  "https://www.pathofexile.com/character-window/get-items"
     :form-params {"accountName" username
                   "character" character
                   "realm" realm}
     :method :post})))

(defn characters
  "Get all the characters on an account (does not filter by league)."
  ([client]
   (characters client "pc"))
  ([{:keys [username] :as client} realm]
   (request
    client
    {:url  "https://www.pathofexile.com/character-window/get-characters"
     :form-params {"accountName" username
                   "realm" realm}
     :method :post})))

(defn prediction
  "Gets a prediction form poeprices.info for an item and league. Takes the item
  in API form and converts it to a string with `poe-info.item/item->str`. Parses
  the json in request body."
  [{:keys [league]} item]
  (d/chain (http/request
            {:url    "https://poeprices.info/api"
             :method :post
             :form-params {"l" league
                           "i" (util/b64-encode (item/item->str item))}
             :headers {"Accept" "application/json"}
             :as :stream})
           #(update % :body json/read-value mapper)))

;;; Old Functions

(defn ^:deprecated stash-item-url
  "Get the URL for the given username and tab index (default 0)."
  ([username] (stash-item-url username 0))
  ([username index]
   (-> "https://www.pathofexile.com/character-window/get-stash-items"
       uri/uri
       (uri/query-map {:league "Delirium"
                       :tabIndex index
                       :accountName username
                       :tabs 1})
       str)))

(defn ^:deprecated get-stash-items
  [cs username index]
  (client/get (stash-item-url username index) {:cookie-store cs}))

(defn ^:deprecated my-account-url
  "THe URL for the my-account page."
  []
  "https://www.pathofexile.com/my-account")

(defn ^:deprecated find-sess-cookie
  "Return the cookie object that has the name POESESSID."
  [cs]
  (first (filter #(= (.getName %) "POESESSID") (.getCookies cs))))

(defn ^:deprecated make-cs
  "Make a cookie store with the POESESSID cookie set to the given id."
  [id]
  (def cs (clj-http.cookies/cookie-store))

  (client/get (my-account-url) {:cookie-store cs})

  (def sess-cookie (find-sess-cookie cs))

  (.setValue sess-cookie id)

  cs)

(defn ^:deprecated get-character-items
  "cs is a cookie store, params is a map with the \"accountName\", \"realm\", and \"character\". This triggers a post request."
  [cs params & {:as opts}]
  (let [default {:form-params (select-keys params [:accountName, :realm, :character])
                 :cookie-store cs
                 :as :json}
        options (merge default opts)]
    (client/post "https://www.pathofexile.com/character-window/get-items"
                 options)))

(defn ^:deprecated get-characters
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



(defn ^:deprecated poeprices-prediction
  [league item-data & {:as opts}]
  (let [defaults {:form-params
                  {:l league
                   :i (util/b64-encode item-data)}
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
