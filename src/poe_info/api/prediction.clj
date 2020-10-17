(ns poe-info.api.prediction
  (:require [clojure.edn :as edn]
            [jsonista.core :as json]
            [manifold.deferred :as d]
            [org.httpkit.client :as http]
            [pl.danieljanus.tagsoup :as tagsoup]))

(defn element?
  [e]
  (keyword? (first e)))

(defn filter-els
  [f e?]
  (let [elements (if (element? e?)
                   (tagsoup/children e?)
                   e?)]
    (filter f elements)))

(defn filter-el
  [f elements]
  (let [tag? (filter-els f elements)]
    (assert (>= 1 (count tag?)))
    (first tag?)))

(defn get-id
  "IDs are strings"
  [id elements]
  (filter-el #(= id (:id (tagsoup/attributes %))) elements))

(defn get-tags
  [tag elements]
  (filter-els #(= tag (tagsoup/tag %)) elements))

(defn get-tag
  [tag elements]
  (filter-el #(= tag (tagsoup/tag %)) elements))

(defn parse-tooltip
  [html]
  (let [raw-str (->> html
                     (get-tag :body)
                     (get-tag :form)
                     (get-id "grid")
                     (get-tag :script)
                     (tagsoup/children )
                     first
                     (re-seq #"\"([^\"])+\"")
                     first
                     first
                     )]
    (subs raw-str 1 (- (count raw-str) 1))))

(defn tooltip->price-table
  [tooltip-str]
  (->> tooltip-str
       (tagsoup/parse-string)
       (get-tag :body)
       (get-tags :p)
       last
       (get-tag :table)))

(defn parse-price-paragraph
    [p]
    (let [children (tagsoup/children p)
          price (nth children 2)
          currency (nth children 4)]
      [:price (edn/read-string price) (keyword currency)]))

(defn parse-response
  [resp]
  (let [table (tooltip->price-table (parse-tooltip (tagsoup/parse (:body resp))))

        tds (get-tags :td (get-tag :tr table))

        min-td (first (get-tags :p (first tds)))
        min  (parse-price-paragraph min-td)

        max-td (first (get-tags :p (nth tds 2)))
        max  (parse-price-paragraph max-td)]
    {:min min
     :max max
     :avg (/ (+ min max) 2)
     :raw-table table}))

(defn raw-prediction
  [item]
  (http/request
   {:url "https://www.poeprices.info/submitstash"
    :method :post
    :as :stream
    :form-params {"stashitemtext" (json/write-value-as-string
                                   {"numTabs" 1
                                    "tabs" [{"i" 0
                                             "n" "tab"}]
                                    "items" [item]})
                  "auto2" "auto"
                  "myshops" ""
                  "useML" "useML"
                  "submit" "SubmitStash"
                  "myaccounts" ""}}))

(defn json-prediction
  [item]
  (d/chain (raw-prediction item)
           parse-response))
