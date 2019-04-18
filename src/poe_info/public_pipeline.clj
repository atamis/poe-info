(ns poe-info.public-pipeline
  (:require [kinsky.async :as kf]
            #_[kinsky.client :as client]
            [clojure.core.async :as a :refer [go <! >!]]
            [aleph.http :as http]
            [clojure.data.json :as json]
            [monger.core :as mg]
            [monger.collection :as mc]
            [taoensso.timbre :as t]
            [manifold.deferred :as d]
            [byte-streams :as bs]

            [poe-info.config :as config]

            [poe-info.api :as api]))

;; Use stash_change_id to request from endpoint

;; "The property stashes.id is unique across all accounts"

(defn deferred->chan
  [deferred]
  (let [c (a/chan)
        f #(a/>!! c %)]
    (d/on-realized deferred f f)
    c))

(def stashes-topic "public-stashes")
(def items-topic "public-items")
(def table-name "public-items")

(def kf-cfg {:bootstrap.servers (config/config :kafka)})

(defn dummy-requester
  []
  (let [prod (kf/producer kf-cfg :keyword :json)]
    (t/info "Starting dummy requster")
    (doseq [name ["pub-stash-1" "pub-stash-2" "pub-stash-3"]]
      (t/info "Loading " name)
      (let [resp (with-open [f (clojure.java.io/reader name)]
                   (json/read f))]
        (->> (resp "stashes")
             (map (fn [stash] {:topic stashes-topic :value stash}))
             (a/onto-chan prod)
             (a/<!!)
             )
        ))
    (t/info "Finished dummy requester")
    ))

(defn stash-requester
  [id]
  (a/<!! (a/go
       (t/info "Starting stash requester with id" id)
       (let [prod (kf/producer kf-cfg :keyword :json)]
         (<!
          (a/go-loop [id id]
            (t/info "Loading with id" id)
            (let [json (-> (http/get (api/public-stash-tabs-url id))
                       deferred->chan
                       <!
                       :body
                       bs/to-string
                       json/read-str
                       )
                  #_json #_(with-open [f (clojure.java.io/reader
                                      (:body (api/public-stash-tabs-url id)))]
                         (json/read f))]
              (t/info "Downloaded " (count (json "stashes")) "stashes")
              (->> (json "stashes")
                   (map (fn [stash] {:topic stashes-topic :value stash}))
                   (a/onto-chan prod)
                   (a/<!)
                   )
              (t/info "Finished loading with id" id)
              (if (= id (json "next_change_id"))
                (do
                  (t/info "Identical ids, sleeping for 20s")
                  (<! (a/timeout 20000)))
                (do
                  (t/info "Different ids, throttling 1s")
                  (<! (a/timeout 1000))))
              (recur (json "next_change_id")))
              )
            ))

       (t/info "Stopping stash requester")
       )
       )
  )

(defn stash-processor
  [record done]
  (a/go
    (if-let [stash (:value record)]
      (do
        (->> (let [context (select-keys stash [:id :public :accountName :lastCharacterName :stash :stashType :league])]
               (map #(assoc % :context context) (:items stash))
               )
             (map (fn [item] {:topic items-topic :value item}))
             (a/onto-chan done) ;; autocloses
             (<!)
             )
        )
      (do (println record)
          (a/close! done))
      )

    )
  )

(defn stash-processors
  [n]
  (let [[in-ch cntrl] (kf/consumer
                    (merge kf-cfg
                           {:topic stashes-topic
                            :group.id (str (gensym))
                            :auto.offset.reset "earliest"})
                    :keyword :json)
        prod (kf/producer kf-cfg :keyword :json)
        ]

    (t/info "Starting stash-processor pipeline")
    (a/pipeline-async
     n
     prod
     stash-processor
     in-ch
     false
     )
    (t/info "Finished stash-processor")
    ))

(defn timed-loop
  ([timeout control init tick] (timed-loop timeout control init tick (constantly nil)))
  ([timeout control init tick done]
   (a/go-loop [s init]
     (let [time-chan (a/timeout timeout)
           [_ selected] (a/alts! [control time-chan])]
       (condp = selected
         control (done s)
         time-chan (recur (tick s))
         )
       ))))

(defn add-id
  [{:keys [id] :as item}]
  (when item
    (assoc item :_id id)))

(defn persister
  []
  (let [[ch cntrl] (kf/consumer
                    (merge kf-cfg
                           {:topic items-topic
                            :group.id (str (gensym)) #_"persister"
                            :auto.offset.reset "earliest"})
                    :keyword :json)
        {:keys [conn db]} (mg/connect-via-uri (config/config :mongo-uri))
        control (a/chan)
        ]
    (a/go
      
      (t/info "Starting persister")

      (t/info "Starting persister tracker")
      (timed-loop 60000 control nil
                  (fn [_] (t/info "Items persisted: " (mc/count db table-name)))
                  (fn [_] (t/info "Persister tracker done")))

      (<! (a/go-loop []
            (when-let [record (a/<! ch)]
              (when-let [new-item (add-id (:value record))]
                #_(println new-item)

                (mc/update-by-id db table-name (:_id new-item)
                                 new-item {:upsert true}))
              #_(println record)
              ;; If we get nil, the channel has closed for some reason.
              (recur))))
      (t/info "Finished persister"))
    ))

(comment

  (def db (:db (mg/connect-via-uri (config/config :mongo-uri))))

  )
  
