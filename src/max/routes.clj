(ns max.routes
  (:require
   [compojure.core :refer [defroutes GET POST ANY]]
   
   [max.index :as index]
   [max.errors :as errors]
   [max.posts :as posts]))


(defroutes raw-app
  (GET "/new" request (posts/page-new request))
  (GET "/" request (index/page-index request))
  (ANY "*" request (errors/page-404 request)))