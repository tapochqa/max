(ns max.posts
  (:require
   [hiccup2.core :as hiccup]
   [java-time.api :as jt]
   
   [max.helper :as helper]))

(defn date
  []
  (jt/format "dd.MM.yyyy"
    (jt/local-date-time)))


(defn page-new
  [request]
  (helper/html-response request
    (hiccup/html 
      [:br]
      [:form.center {}
      [:textarea
              {:id   "post"
               :name "post"
               :type "textarea"
               :rows 40
               :cols 40}
       (format "%s\n\n"
         (date))]
      [:br] [:br]
      [:button {:id "save-button"} "Сохранить в файл"]
      [:script (hiccup/raw (slurp "js/save-as-file.js"))]]
      
      )))