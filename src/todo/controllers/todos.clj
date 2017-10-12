(ns todo.controllers.todos
    (:require [compojure.core :refer [defroutes GET POST]]
              [clojure.string :as str]
              [ring.util.response :as ring]
              [todo.views.todos :as view]
              [todo.models.todo :as model]))

(defn index []
(view/index (model/all)))

(defn create
    [todo]
    (when-not (str/blank? todo)
      (model/create todo))
    (ring/redirect "/"))

(defn delete [todo]
    (when-not (str/blank? todo)
      (model/delete todo))
      (ring/response "todo deleted"))

(defroutes routes
    (GET  "/" [] (index))
    (POST "/" [todo] (create todo))
    (POST "/delete" [todo] (delete todo)))