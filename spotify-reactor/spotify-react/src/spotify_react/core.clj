(ns spotify-react.core
  (:require [clj-spotify [util :as spu]
                         [core :as spc]]
            [clojure.pprint :as pp]))

(def client-id "29b14ddace06413b96fb9c904c89b706")
(def client-secret "f5a1c1087457445fb48181517ece5605")

(defn track-tuple
  [{:keys [id name artists]}]
  {:id id
   :name name
   :artist (-> artists
               first
               :name)})

(defn get-song
  [song token]
  (let [query {:q song
               :type "track"
               :limit 1}]
    (-> (spc/search query token)
        :tracks
        :items
        first
        track-tuple)))

(defn -main
  [& args]
  (->> (spu/get-access-token client-id client-secret)
       (get-song "high hopes")
       pp/pprint))
