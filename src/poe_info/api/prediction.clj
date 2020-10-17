(ns poe-info.api.prediction
  (:require [clojure.edn :as edn]
            [jsonista.core :as json]
            [manifold.deferred :as d]
            [org.httpkit.client :as http]
            [pl.danieljanus.tagsoup :as tagsoup]))

(defn element?
  "Is a hiccup/tagsoup element?"
  [e]
  (keyword? (first e)))

(defn filter-els
  "Filter a list of elements or the children of a single element based on a
  predicate."
  [f e?]
  (let [elements (if (element? e?)
                   (tagsoup/children e?)
                   e?)]
    (filter f elements)))

(defn filter-el
  "Filter a list of elements or the children of a single element based on a
  predicate. If it returns more than one element, throw an exception."
  [f elements]
  (let [tag? (filter-els f elements)]
    (assert (>= 1 (count tag?)))
    (first tag?)))

(defn get-id
  "Get an element from a list of elements or the children of an element by HTML
  ID. IDs are strings. Throw an exception if you find more than one matching
  element."
  [id elements]
  (filter-el #(= id (:id (tagsoup/attributes %))) elements))

(defn get-tags
  "Get an element from a list of elements or the children of an element by HTML
  tag. Tags are keywords."
  [tag elements]
  (filter-els #(= tag (tagsoup/tag %)) elements))

(defn get-tag
  "Get an element from a list of elements or the children of an element by HTML
  tag. Tags are keywords. Throw an exception of you find more than one matching
  element."
  [tag elements]
  (filter-el #(= tag (tagsoup/tag %)) elements))

(defn parse-tooltip
  "Parse the first items's tooltip string from the parsed HTML."
  [html]
  (let [raw-str (->> html
                     ;; The tooltip is stored in a Javascript string in the only
                     ;; script tag that is in the grid.
                     ;;
                     ;; The grid contains the item icons and the scripts to show
                     ;; their tooltips, and there should be only one item
                     ;; and one script.
                     (get-tag :body)
                     (get-tag :form)
                     (get-id "grid")
                     (get-tag :script)
                     (tagsoup/children)
                     first
                     ;; Regex for a string. There should be only one, so we
                     ;; take the first one.
                     (re-seq #"\"([^\"])+\"")
                     first
                     first
                     )]
    ;; String has some extraneous characters because the regex isn't very well
    ;; made.
    (subs raw-str 1 (- (count raw-str) 1))))

(defn tooltip->price-table
  [tooltip-str]
  ;; Tooltip is a string of html.
  (->> tooltip-str
       (tagsoup/parse-string)
       ;; The string doesn't actually have a body, TagSoup adds one.
       (get-tag :body)
       (get-tags :p)
       last
       (get-tag :table)))

(defn parse-price-paragraph
  ;; These are the paragraphs that contain the actual
  ;; min and max price.
  [p]
  (let [children (tagsoup/children p)
        price (nth children 2)
        currency (nth children 4)]
    [:price (edn/read-string price) (keyword currency)]))

(defn avg-prices
  "Average a list of prices. Only valid if all prices have the same currency."
  ;; This seems to hold true for all predictions.
  [prices]
  (/ (reduce + (map second prices)) (count prices)))

(defn parse-response
  [resp]
  ;; Min is in the first TD, max is in the second TD with some other stuff. The
  ;; pricing info is in the first paragraph tag (the max TD has some additional
  ;; paragraphs that don't matter).
  (let [table (tooltip->price-table (parse-tooltip (tagsoup/parse (:body resp))))

        tds (get-tags :td (get-tag :tr table))

        min-td (first (get-tags :p (first tds)))
        min  (parse-price-paragraph min-td)

        max-td (first (get-tags :p (nth tds 2)))
        max  (parse-price-paragraph max-td)]
    {:min min
     :max max
     :avg (avg-prices [min max])
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
