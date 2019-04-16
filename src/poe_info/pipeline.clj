(ns poe-info.pipeline
  (:require [kinsky.async :as async]
            [kinsky.client :as client]
            [clojure.core.async :as a :refer [go <! >!]]
            [clj-http.client :as http]

            [poe-info.config :as config]
            [poe-info.api :as api]
            [poe-info.item :as item]))

(def unpriced-items-topic "unpriced-items")
(def persistence-topic "persistence")
(def price-change-topic "price-change")


;; http client requests tabs, adds context to items, pushes
;; items to unpriced-items queue

;; service prices items from unpriced-items queue, pushes
;; them to persistence queue

;; service loads items from persistence queue into database,
;; pushes price changes to another queue


;; service reads price differential queue and alerts the user
;; to large price differentials.


(def cfg {:bootstrap.servers (config/config :kafka)})

(defn producer
  []
  (async/producer cfg :keyword :edn))

(defn print-consumer
  []
  (let [ch (async/consumer (merge cfg
                                  {:topic "test"
                                   :duplex? true
                                   :group.id "print-consumer-2"
                                   :auto.offset.reset "earliest"})
                           (client/keyword-deserializer)
                           (client/edn-deserializer))]
    (a/go-loop []
      (when-let [record (a/<! ch)]
        (println (clojure.pprint/pprint record))
        (recur)))))

(defn tab-requester
  []
  (let [username (config/config :username)
        cs (api/make-cs (config/config :poesessid))
        watched-tabs (config/config :watched-tabs)
        prod (async/producer cfg :keyword :json)]
    (loop []
      (doseq [index watched-tabs]
        (println "Loading tab " index)
        (as-> (api/stash-item-url username index) $
          (http/get $ {:cookie-store cs :as :json})
          (:body $)
          (api/stash-items-context username $)
          (map (fn [item]
                 {:topic unpriced-items-topic
                  :key (:id item)
                  :value item}) $)
          (a/onto-chan prod $ false)
          (a/<!! $))
        )
      (println "Done")
      (java.lang.Thread/sleep 10000)
      (recur)
      )))

(defn item-pricer-pipeline
  [record result]
  (a/go
    (if-let [item (:value record)]
      (do (println "Pricing " (:id item))
          (api/poeprices-prediction
           (:league item)
           (item/item->str item)
           :async? true
           :respond-callback
           (fn [resp]
             (a/go
               (>! result {:topic persistence-topic
                           :key (:id item)
                           :value (assoc item
                                         :price-prediction
                                         (-> resp :body))})
               (a/close! result)))
           :raise-callback #(a/go (println %)
                                  (a/close! result)
                                  )))
      (a/close! result))))

(defn item-pricer
  []
  (let [prod (async/producer cfg :keyword :json)
        [ch cntrl] (async/consumer
                    (merge cfg
                           {:topic unpriced-items-topic
                            :duplex? true
                            :group.id "item-pricer"
                            :auto.offset.reset "earliest"})
                    :keyword :json)]

    (a/pipeline-async
     8 ;; parallellism
     prod
     item-pricer-pipeline
     ch
     false ;; close the channel?
     )))
