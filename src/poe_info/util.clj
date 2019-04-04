(ns poe-info.util)

(defn key-map
  [f seq]
  (into {} (map (fn [x] [x (f x)]) seq))
  )

(defn inc-or-1
  "Increase n by 1, or return 1 if n is nil"
  [n]
  (if n (inc n) 1))

(defn histogram
  "Count the number of times each element in the collection appears."
  [seq]
  (reduce #(update %1 %2 inc-or-1) {} seq))

(defn extract-first
  [pred seq]
  (let [[n m] (split-with (complement pred) seq)
        item (first m)]
    [item (concat n (rest m))]))
