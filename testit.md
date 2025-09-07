Alles klar, freut mich, dass jetzt alles läuft! 😊

Um Grafana und das gesamte Monitoring-Setup zu testen, musst du Folgendes tun:

### 1. Grafana-Dashboard öffnen
Öffne deinen Webbrowser und navigiere zur Grafana-Benutzeroberfläche unter **`http://localhost:3000`**. Melde dich mit den Standardanmeldedaten an, die du in deiner `docker-compose.yml` festgelegt hast:
* **Benutzer:** `admin`
* **Passwort:** `admin`

### 2. Datenquellen überprüfen
Als Erstes solltest du sicherstellen, dass Grafana mit allen Datenquellen (Prometheus, Jaeger, Elasticsearch) verbunden ist.
1.  Klicke im Menü links auf das Zahnrad-Symbol (Konfiguration).
2.  Wähle `Data sources`.
3.  Hier sollten Einträge für **Prometheus**, **Jaeger** und **Elasticsearch** zu sehen sein.
4.  Klicke auf jede Datenquelle und dann auf **Save & test**. Du solltest die Meldung "Data source is working" oder Ähnliches sehen. Wenn nicht, liegt ein Problem mit der Verbindung vor.

***

### 3. Log-Monitoring (Kibana) testen
Grafana ist nicht die beste Wahl, um Roh-Logs zu durchsuchen. Dafür ist **Kibana** da. 
1.  Öffne einen neuen Tab in deinem Browser und navigiere zu Kibana unter **`http://localhost:5601`**.
2.  Gehe zum Menü `Analytics` und wähle **Discover**.
3.  Klicke auf `Create data view` und gib `logstash-*` als Index-Muster ein. Klicke dann auf `Create data view`.
4.  Wähle deine neu erstellte Datenansicht aus. Du solltest eine Liste der Logs sehen, die Logstash von deinen Microservices gesammelt hat. Wenn hier Daten erscheinen, funktioniert der gesamte ELK-Stack (Elasticsearch, Logstash, Kibana) korrekt.

***

### 4. Tracing-Monitoring (Jaeger) testen
Um zu überprüfen, ob das Tracing funktioniert, musst du eine Abfrage über die Benutzeroberfläche deiner Microservices ausführen.
1.  Sende eine HTTP-Anfrage an deine Anwendung, z. B. indem du die URL **`http://localhost:80`** im Browser aufrufst oder `http://localhost:8081/api/data` für den ersten Microservice.
2.  Öffne die Jaeger-Benutzeroberfläche unter **`http://localhost:16686`**.
3.  Im Menü links kannst du unter **`Search`** nach Services suchen.
4.  Wähle im Dropdown-Menü **`Service`** den Namen deines Microservice aus (z. B. `microservice1`).
5.  Klicke auf **Find Traces**. Du solltest jetzt die Spuren (Traces) deiner Anfragen sehen. Wenn das der Fall ist, funktioniert das Tracing von Jaeger.

***

### 5. Metrik-Monitoring (Prometheus & Grafana) testen
Jetzt, da du weißt, dass deine Microservices Traces senden, kannst du die Metriken in Prometheus überprüfen.
1.  Öffne Prometheus unter **`http://localhost:9090`**.
2.  Gib im Suchfeld oben einen Metriknamen ein, wie z. B. `http_server_requests_seconds_count`, um die Anzahl der HTTP-Anfragen zu sehen. Wenn die Autovervollständigung funktioniert und du Werte siehst, sammelt Prometheus erfolgreich Daten von deinen Microservices.
3.  Kehre zu **Grafana** zurück.
4.  Klicke im Menü links auf das **+**-Symbol und wähle **Dashboard** > **Add new panel**.
5.  Wähle Prometheus als Datenquelle aus und gib die gleiche Metrik (`http_server_requests_seconds_count`) in das Query-Feld ein.
6.  Du solltest eine Grafik sehen, die die Anfragen an deine Anwendung darstellt. Wenn du diese Grafik sehen kannst, sind Prometheus und Grafana erfolgreich für das Metrik-Monitoring konfiguriert.