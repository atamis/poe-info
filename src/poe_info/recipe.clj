(ns poe-info.recipe
  (:require [clojure.core.match :refer [match]]
            [poe-info.util :as util]
            ))

;; This is focused on the identified rescipes
;; Unid'd, qualitied, and both offer more returns, but tend to be organized differently.


(defn betweener
  [low high]
  #(<= low % high))

(def chance-ilvl? (betweener 1 59))
(def chaos-ilvl? (betweener 60 74))
(def regal-ilvl? (betweener 75 100))

(defn helmet?
  [{:keys [category]}]
  (match category
    {:armour ["helmet"]} true
    :else false))

(defn chest?
  [{:keys [category]}]
  (match category
    {:armour ["chest"]} true
    :else false)
  )

(defn gloves?
  [{:keys [category]}]
  (match category
         {:armour ["gloves"]} true
         :else false)
  )

(defn boots?
  [{:keys [category]}]
  (match category
         {:armour ["boots"]} true
         :else false)
  )

(defn ring?
  [{:keys [category]}]
  (match category
         {:accessories ["ring"]} true
         :else false)
  )

(defn amulet?
  [{:keys [category]}]
  (match category
         {:accessories ["amulet"]} true
         :else false)
  )

(defn belt?
  [{:keys [category]}]
  (match category
         {:accessories ["belt"]} true
         :else false)
  )

(defn onehander?
  [{:keys [category]}]
  (match category
         {:weapons ["onemace"]} true
         {:weapons ["claw"]} true
         {:weapons ["wand"]} true
         {:weapons ["onesword"]} true
         {:weapons ["shield"]} true
         {:weapons ["sceptre" "onemace"]} true
         {:weapons ["oneaxe"]} true
         :else false)
  )

(defn twohander?
  [{:keys [category]}]
  (match category
         {:weapons ["bow"]} true
         {:weapons ["twoaxe"]} true
         {:weapons ["twosword"]} true
         {:weapons ["twomace"]} true
         {:weapons ["staff"]} true
         :else false)
  )

(def base-set [helmet? chest? gloves? boots?  ring? ring? belt? amulet?])
(def set1 (into base-set [onehander? onehander?]))
(def set1 (into base-set [twohander?]))

(defn extract-set
  [preds orig-seq]
  (loop [preds preds
         seq orig-seq
         out []]
    (if (empty? preds)
      [out seq]
      (let [[pred & remaining-preds] preds
            [item seq] (util/extract-first pred seq)]
        (when item
          (recur remaining-preds seq (conj out item)))))))
