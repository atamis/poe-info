(ns poe-info.gems
  (:require [poe-info.constants :as constants])
  )

;; take a list of numbers
;; return list of numbers indicating the batch size to vendor gems
;; each batch must sum to at least a multiple of 40
;; minimize excess over the multiple of 40
;; batch has maximum size

(def target-multiple 40)

(defn gem-maximum-batches
  "Returns [batches left-overs]"
  [qualities]
  (loop [out []
         cur-batch []
         qualities qualities]
    (if (<= target-multiple (reduce + cur-batch))
      (recur (conj out cur-batch) [] qualities)
      (if (empty? qualities)
        [out cur-batch]
        (recur out (conj cur-batch (first qualities)) (rest qualities))))))

(defn batch-waste
  [batch]
  (rem (reduce + batch) target-multiple))

(defn avg-soln-waste
  [batches]
  (/ (->> batches
          (map batch-waste)
          (reduce +))
     (count batches)))

#_ (gem-maximum-batches '(20 21 40))

(defn find-indexes
  [pred seq]
  (let [pred (if (fn? pred) pred #(= % pred))]
    (loop [seq seq
           out '()
           idx 0]
      (if (empty? seq)
        out
        (let [[car & cdr] seq
              out (if (pred car) (conj out idx) out)]
          (recur cdr out (inc idx)))))))

(defn min-index
  [seq]
  (first (apply min-key second (map-indexed vector seq))))

(defn running-waste
  [batches]
  (loop [batches batches
         out []
         sum 0]
    (if (empty? batches)
      out
      (let [[car & cdr] batches
            sum (reduce + sum car)
            waste (rem sum target-multiple)]
        (recur cdr (conj out waste) sum)))))


(defn aprox-gem-batches
  [qualities]
  (loop [qualities qualities
         out []]
    (if (empty? qualities)
      out
      (let [;; take an inventory load
            inventory (take constants/normal-inventory qualities)
            [batches left-over] (gem-maximum-batches inventory)

            ;; count the leftovers as a batch (totally wasted)
            batches (conj batches left-over)
            wastes (running-waste batches)

            ;; find the value of the best index
            first-best-index (min-index wastes)
            first-best-waste (nth wastes first-best-index)
            ;; find the latest instance of that waste
            latest-best-index (apply max (find-indexes first-best-waste wastes))

            ;; we now know how many batches minimizes the running waste
            best-batches (take (inc latest-best-index) batches)
            best-batch (flatten best-batches)

            ;; removed the gems we just slated for vendoring
            remaining (drop (count best-batch) qualities)
            ]
        (recur remaining (conj out best-batch))))))


(defn lexigraphic-stash-index
  "Starts at 0 for the top left corner, and increases down and to the right.
  Good for a human understandable index (kind of). "
  [{:keys [x y]}]
  (+ y (* x constants/stash-height)))

(defn item-quality
  "Determine the integer value for the quality of an item."
  [{:keys [properties]}]
  (let [{:keys [values]} (first (filter #(= (:name %) "Quality") properties))
        [[quality _]] values
        ]
    (if quality
      (let [[_ s] (re-find #"\+(.+)\%" quality)]
        (Integer/parseInt s)
        )
      0)
    ))

(defn summary
  [gems]
  (->> gems
       :items
       (sort-by lexigraphic-stash-index)
       (map item-quality)
       aprox-gem-batches
       (map (fn [x] [(count x) (batch-waste x)])))
  )


(comment
  (defn load-gem-inventory
    []
    (def gems (json/read-str (slurp "gems.json") :key-fn keyword))
    ))
