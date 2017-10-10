(ns todo.models.migration
    (:require [clojure.java.jdbc :as sql]
              [todo.models.todo :as todo]))

(defn migrated? []
(-> (sql/query todo/spec
                [(str "select count(*) from information_schema.tables "
                        "where table_name='todos'")])
    first :count pos?))

(defn migrate []
    (when (not (migrated?))
        (print "Creating database structure...") (flush)
        (sql/db-do-commands todo/spec
                            (sql/create-table-ddl
                            :todos
                            [[:id :serial "PRIMARY KEY"]
                            [:name :varchar "NOT NULL"]
                            [:created_at :timestamp
                            "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]]))
        (println " done")))