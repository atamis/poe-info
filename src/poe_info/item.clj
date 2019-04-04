(ns poe-info.item)

(def frametype->rarity
  "Convert a frameType to a symbol representing the rarity (including gem)"
  {0 :normal
   1 :magic
   2 :rare
   3 :unique
   4 :gem})


(defn stash-index
  "I 100% do not know what's up with :inventoryId, but this
  unfucks it and puts the real index in :stash-id"
  [{:keys [inventoryId]}]
  (let [[_ s] (re-find #"Stash(.+)"  inventoryId)]
    (when s
      (dec (Integer/parseInt s))
      )))

(defn rarity
  [{:keys [frameType]}]
  (frametype->rarity frameType)
  )
