(ns max.layout
  (:require 
   [hiccup2.core :as hiccup]
   
   [clojure.string :as str]))


(defn head
  [{:keys [title] :as request}]
  (hiccup/html
    
    [:head
     [:link {:href "/style.css"     :rel "stylesheet"}]
     
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport" 
             :content "width=device-width, initial-scale=1"}]
     
     [:link {:rel "icon"
             :href (if (-> request 
                           (get-in [:headers "host"])
                           (str/includes? "localhost"))
                     "/favicon-dev.ico"
                     "/favicon.ico")}]
     
     [:title (if title 
                 title
                 "Макщ")]]))


(def space
  (hiccup/html [:span.space " / "]))


(defn header
  [request]
  (hiccup/html
    [:div.header.center   
     (hiccup/html
       [:span.p "макс.лимонадный.ру"]
       space
       [:a.p {:href "https://limonadny.ru"} "лзм"]
       space
       [:a.p {:href "https://chyay.ru"} "чяй"]
       )]))


(defn footer
  [request]
  (hiccup/html
    [:div.footer.center 
     (hiccup/html 
       [:br][:br][:br][:br][:br][:br][:br][:br][:br][:br]
       [:p "Гарнитура Эс-тэ фансун"]
       [:a {:href "/new"} "Написать новый пост"])]))