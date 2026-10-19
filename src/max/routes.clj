(ns max.routes
  (:require
   [compojure.core :refer [defroutes GET POST ANY]]
   
   [max.index :as index]
   [max.errors :as errors]
   [max.posts :as posts]
   [max.helper :as helper]))


(defroutes raw-app
  (GET "/" request (index/page-index request))
  (GET "/new" request (posts/page-new request))
  (GET "/e/:domain" [domain :as request] 
    (posts/page-show request 
      (format "%s/%s" 
        (-> helper/GLOBALS :txt)
        domain)))
  (ANY "*" request (errors/page-404 request)))