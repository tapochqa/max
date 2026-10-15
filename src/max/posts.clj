(ns max.posts
  (:require
   [hiccup2.core :as hiccup]
   
   [max.helper :as helper]))


(defn page-new
  [request]
  (helper/html-response request
    (hiccup/html 
      [:br]
      [:form {}
      [:textarea
              {:name "text"
               :type "textarea"
               :rows 40
               :cols 40}]]
      
      ))
  )