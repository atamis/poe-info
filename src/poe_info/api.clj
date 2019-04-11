(ns poe-info.api
  (:require
   [clj-http.client :as client]
   [clj-http.cookies :as cookies]))

(defn stash-item-url
  "Get the URL for the given username and tab index (default 0)."
  ([username] (stash-item-url username 0))
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
