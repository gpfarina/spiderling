(ns spiderling.server
  (:require [org.httpkit.server :as http]
            [reitit.ring :as ring]
            [reitit.ring.middleware.muuntaja :as rmuuntaja]
            [muuntaja.core :as m]))

(def muuntaja-instance
  (m/create
   (assoc m/default-options
          :default-format "application/json")))
(def app
  (ring/ring-handler
   (ring/router
    [["/ping" {:get (fn [_] {:status 200 :body {:message "pong 🕷️"}})}]])
   (ring/create-default-handler {:middleware [(rmuuntaja/format-middleware muuntaja-instance)]})))

(defonce server (atom nil))

(defn start!
  ([port] (reset! server (http/run-server app {:port port}))
          (println (format "Spiderling server running on port %d" port))))

(defn stop! []
  (when-let [s @server]
    (s)
    (reset! server nil)
    (println "🛑 Spiderling server stopped.")))

(defn -main [& [port]]
  (let [port (Integer/parseInt (or port "8089"))]
    (start! port)
    ;; add shutdown hook
    (.addShutdownHook (Runtime/getRuntime)
                      (Thread. #(stop!)))))
