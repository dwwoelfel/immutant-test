(ns immutant-test.core
  (:require [immutant.web :as web]
            [immutant.web.async :as async]))

(defonce connections (atom #{}))

(add-watch connections nil (fn [_ _ _ new]
                             (mapv #(async/send! %
                                                 (with-out-str (clojure.pprint/print-table (map (fn [c] {:channel c
                                                                                                         :open? (async/open? c)})
                                                                                                new))))
                                   new)))

(defn make-html [body-str]
  (str
   "<html>"

   "<head>"
   "<script type='text/javascript'>"
   "window.w = new WebSocket('ws://' + document.location.host + '/ws');"
   "w.onopen = function(e) { console.log(e) };"
   "w.onclose = function(e) { console.log(e) };"
   "w.onmessage = function(e) { console.log(e); document.getElementById('connections').innerText = e.data; };"
   "w.onerror = function(e) { console.log(e) };"
   "</script>"
   "</head>"

   "<body>"
   body-str
   "</body>"

   "</html>"))

(def handler
  (fn [req]
    (if (= (:uri req) "/ws")
      (async/as-channel req
                        {:on-open (fn [ch]
                                    (println "new channel" ch)
                                    (swap! connections conj ch))
                         :on-close (fn [ch args]
                                     (println "channel closed" ch args)
                                     (swap! connections disj ch))
                         :on-message (fn [ch msg]
                                       (println "new message for" ch msg))
                         :on-error (fn [ch throwable]
                                     (println "error" throwable))})
      {:body (make-html (str "<p>Connections :</p><p><pre id='connections'>"
                             (with-out-str (clojure.pprint/print-table (map (fn [c] {:channel c
                                                                                     :open? (async/open? c)})
                                                                            @connections)))
                             "</pre></p>"))
       :status 200})))

(defn -main []
  (let [port 23142]
    (println (str "Starting server on http://localhost:" port))
    (web/server (web/run
                  handler
                  {:port port
                   :host "0.0.0.0"}))))
