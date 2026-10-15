(ns max.core
  (:require
   [ring.adapter.jetty :as jetty]
   [ring.middleware.resource :as ring.resource]
   [ring.middleware.file :as ring.file]
   [ring.middleware.file :as ring.file]
   [ring.middleware.params :as ring.params]
   [ring.middleware.keyword-params :as ring.keyword-params]
   [ring.middleware.session :as ring.session]
   [ring.middleware.session.cookie :as ring.cookie]
   [ring.middleware.reload :as ring.reload]
   
   [clojure.tools.logging :as log]
   [clojure.test :as test]
   [clojure.spec.alpha :as spec]
   
   [compojure.core :as compojure]
   
   [hiccup2.core :as hiccup]
   
   [max.routes :as routes]
   
   [max.errors :as errors]
   
   [max.helper :as helper]))






(defn wrap-production-errors [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (log/error "Error on" (:uri request) ":" (.getMessage e))
        (.printStackTrace e)
        (errors/page-500 request)))))


(def wrapped-app
  (-> routes/raw-app
    (ring.keyword-params/wrap-keyword-params)
    (ring.params/wrap-params)
    (ring.resource/wrap-resource "public")
    (wrap-production-errors)))


(def app
  (ring.reload/wrap-reload #'wrapped-app))


(defn -main []
  (jetty/run-jetty #'app {:port 3003 :join? true}))







