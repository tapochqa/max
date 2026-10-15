(ns max.errors
  (:require
   [hiccup2.core :as hiccup]
   
   [max.helper :as helper]))


(defn page-404
  [request]
  (helper/html-response (assoc request :bare true)
    (hiccup/html 
      [:h1 "Чоч."]
      [:a {:href "/"} "На гл."])))


(defn page-500
  [request]
  (helper/html-response (assoc request :bare true)
    (hiccup/html 
      [:h1 "500??? На статическом сайте?"])))