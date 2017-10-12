(ns todo.web
    (:require [compojure.core :refer [defroutes]]
              [ring.adapter.jetty :as ring]
              [compojure.route :as route]
              [ring.middleware.json :refer [wrap-json-params]]
              [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [todo.controllers.todos :as todos]
              [todo.views.layout :as layout]
              [todo.models.migration :as schema])
    (:gen-class))

(defroutes routes
    todos/routes
    (route/resources "/")
    (route/not-found (layout/four-oh-four)))


(def application (wrap-json-params routes (assoc-in site-defaults [:security :anti-forgery] false)))

(defn start [port]
    (ring/run-jetty application {:port port
                                :join? false}))

(defn -main []
    (schema/migrate)
    (let [port (Integer. (or (System/getenv "PORT") "8080"))]
        (start port)))

