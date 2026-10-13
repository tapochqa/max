(defproject max "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies 
  [[org.clojure/clojure 
    "1.12.0"]
   
   [ring/ring-core  
    "1.14.2"]
   
   [ring/ring-devel 
    "1.14.2"]
   
   [ring/ring-jetty-adapter 
    "1.14.2"]
   
   [compojure 
    "1.7.1"]
   
   [hiccup 
    "2.0.0"]

   [com.github.igrishaev/pg2-core 
    "0.1.49"] ; maybe every site gonna end up havin a db?
   
   [cheshire 
    "6.1.0"]
   
   [ch.qos.logback/logback-classic 
    "1.4.11"]
   
   [org.clojure/tools.logging 
    "1.2.4"]
                 
   [clojure.java-time 
    "1.2.0"]
   
   ]
  :repl-options {:init-ns max.core}
  :main max.core)
