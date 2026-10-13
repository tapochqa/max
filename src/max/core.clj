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
   
   [max.helper :as helper]))


(defn page-index
  [request]
  (helper/html-response request
    (hiccup/html [:h1 
                  (-> ["Сайт-визитка Максима Саковича"
                       "Портфолио звукорежиссера М. Саковича"
                       "Здесь могла быть ваша реклама"
                       "Объект культурного наследия. Охраняется государством. Ведется видеонаблюдение."
                       "Студия аддиктивных технологий Максима Саковича"
                       "There is nothing here! Здесь ничего нет! 這裡什麼都沒有!"
                       "Бесплатный сайт Максима Саковича"
                       "Сайт содержит информацию"
                       "Ошибка 508. Перезагрузите смартфон."
                       "Вы используете последнюю версию сайта"
                       "Пожалуйста, поверните устройство"
                       "Перезагрузите страницу"
                       "Запись, сведение, песни под ключ"
                       "Официальный сайт Максима Саковича"
                       "Meeeeeeeeeeeeeeeeeeee:3"
                       "Сайт Максима Саковича работает круглосуточно, без перерывов и выходных"
                       "Звукорежиссер Максим Сакович"
                       "Пожертвования на реставрацию сайта Максима Саковича принимаются в валюте Российской Федерации"
                       "Эзотерика, философия, психология, нетрадиционная медицина, йога, цигун, боевые искусства, астрология, магия, руны, таро, благовония, аромотерапия, фэн-шуй, амулеты, сувениры, этническая и медитативная музыка"
                       "Давайте лучше встретимся"
                       "Индивидуальный предприниматель Сакович Максим Дмитриевич. ИНН 346100829639, ОГРНИП 322344300018033."
                       "Битмейкер Максим Сакович"
                       "Дискография группы «Максим Сакович»"
                       "Сайт содержит нецензурную брань"
                       "Я не использую куки"
                       "Здравствуйте!"
                       "Спасибо!"
                       "Курение возле сайта Максима Саковича запрещено"
                       "Ищу подработку"
                       ]
                    shuffle
                    first)
                
                [:br] [:br]
                
                [:p "Написал более 50 треков в составе группы «" 
                 [:a {:href "https://t.me/limonadny_zavod"} "Максим Толстов"] "»."]

                [:p "Автоматизировал свою жизнь при помощи " 
                 [:a {:href "https://github.com/tapochqa?tab=repositories"} "пет-проектов на кложе"] "."]
                
                [:p "В свободное время профессионально пью чай."]
                  
                [:p "У меня есть " [:a {:href "https://vk.ru/meloxicam"} "вк"] 
                 ". У меня есть " 
                 [:a {:href "https://www.instagram.com/briskovi4"} "инста"] 
                 ". <-- инстаграм — террористы! и враги"]
                [:p "У меня есть " 
                 [:a {:href "https://www.avito.ru/brands/1cee57cc05efe1fa331bdf1c7813fe5b"} "авито"] 
                 ". У меня есть " 
                 [:a {:href "https://www.youtube.com/@TAPOCHQA"} "ютуб"] 
                 "."]
                [:p "У меня есть электропочта " 
                 [:a {:href "mailto:max@limonadny.ru"} "max@limonadny.ru"] 
                 ". И лс "
                 [:a {:href "https://t.me/briskovic"} "в телеге"]
                 "."]])))


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


(compojure/defroutes raw-app
  (compojure/GET "/" request (page-index request))
  
  (compojure/ANY "*" request (page-404 request)))


(defn wrap-production-errors [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (log/error "Error on" (:uri request) ":" (.getMessage e))
        (.printStackTrace e)
        (page-500 request)))))


(def wrapped-app
  (-> raw-app
    (ring.keyword-params/wrap-keyword-params)
    (ring.params/wrap-params)
    (ring.resource/wrap-resource "public")
    (wrap-production-errors)))


(def app
  (ring.reload/wrap-reload #'wrapped-app))


(defn -main []
  (jetty/run-jetty #'app {:port 3003 :join? true}))







