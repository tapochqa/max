(ns max.helper
  (:require
   [hiccup2.core :as hiccup]
   [ring.util.request :as ring-request]
   [cheshire.core :as json]
   [java-time.api :as jt]
   
   [clojure.string :as str]
   [clojure.tools.logging :as log]
   
   [max.layout :as layout]))


(def GLOBALS
  {:txt "../max-img/txt"})


(defn parse-json-body [request]
  (let [body (ring-request/body-string request)]
    (when (and body (not (empty? body)))
      (json/parse-string body true))))


(defn html-response
  [request body]
  #_(log/info request)
  (let [{:keys [uri request-method bare status] :or {status 200}} request]
    {:status status
     :headers {"Content-Type" "text/html"}
     :body
     (str
       (layout/head request)
       (if-not bare (layout/header request))
       (hiccup/html [:main body])
       (if-not bare (layout/footer request)))}))


(defn replace-month-genitive
  [formatted]
  
  (-> formatted
    (str/replace   "January" "января")
    (str/replace  "February" "февраля")
    (str/replace     "March" "марта")
    (str/replace     "April" "апреля")
    (str/replace       "May" "мая")
    (str/replace      "June" "июня")
    (str/replace      "July" "июля")
    (str/replace    "August" "августа")
    (str/replace "September" "сентября")
    (str/replace   "October" "октября")
    (str/replace  "November" "ноября")
    (str/replace  "December" "декабря")))

