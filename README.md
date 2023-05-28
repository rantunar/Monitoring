# Account multiple database api monitoring

###Step 1: 
Edit monitoring/prometheus.yml file and put your local ip in static_configs.target.
###Step 2:
Run command >> "docker-compose up -d" (wait for sometime before running application)
###Step 3:
Run Monitoring application
###Step 4:
Open Grafana in browser - http://localhost:3000 with username = admin, password = admin(skip password change option)
###Step 5:
In terminal/command prompt run command "docker ps" and copy the container id of prom/prometheus:v2.14.0 then run below command - 
docker inspect  -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "container id from above"
Above command will provide an IP-Address.
###Step 6:
In Grafana browser goto Configuration > Add data source > Prometheus > in http url put "http://{ip address copied from above step}:9090" > Click "Save & Test"
###Step 7:
Now goto Dashboards > Browse > Click Import > Click upload json file > choose the grafana-dashboard.json file from monitoring folder
###Step 8:
Now your dashboard is imported but the datasource of the dashboard needs to refresh so click "Edit" of each panel and choose "Prometheus" in datasource dropdown and click "Run query" and "Apply"
###Step 9:
Dashboard is ready to work and to test it -  run command -> "docker ps" -> copy mysql container id and run -> "docker stop {container id}", dashboard panel of mysql connection must change to "1" and again if you run -> "docker start {container id}", panel data must become "0"
###Step 10:
To test /account/create(500 error) panel, import the postman collection "Account.postman_collection.json" and hit the create endpoint 2 times with same "accountNumber" 