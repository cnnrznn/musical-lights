(ns spotify-react.core
  (:require [clj-spotify [util :as spu]
                         [core :as spc]]
            [clojure.pprint :as pp]))

(def client-id "29b14ddace06413b96fb9c904c89b706")
(def client-secret "f5a1c1087457445fb48181517ece5605")
(def token (spu/get-access-token client-id client-secret))

(defn track-tuple
  [{:keys [id name artists]}]
  {:id id
   :name name
   :artist (-> artists
               first
               :name)})

(defn search-track
  [song]
  (let [query {:q song
               :type "track"
               :limit 1}]
    (-> (spc/search query token)
        :tracks
        :items
        first
        track-tuple)))

(defn req-track
  [tid]
  (let [query {:id tid}]
    (spc/get-a-track query token)))

(defn req-analysis
  [tid]
  (let [query {:id tid}]
    (spc/get-audio-analysis-for-a-track query token)))

(defn print-ident
  [x]
  (pp/pprint x)
  x)

(defn -main
  [& args]
  (->> "high hopes"
       (search-track)
       print-ident
       :id
       req-analysis
       :sections
       (map (fn [{:keys [start confidence]}]
              {:start start
               :confidence confidence}))
       pp/pprint))
