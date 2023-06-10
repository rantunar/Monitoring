# Account multiple database api monitoring

![alt text](https://github.com/rantunar/Monitoring/blob/main/MonitoringFlowDiagram.png?raw=true)

### Details:
1. Advance alert & monitoring system helps to get the notification and solve the application problem before end user report that issue in production, it keeps checking multiple database connectivity, kafka connectivity and any API failures or slow response to notify the concern persons in advance. (Based on the input, data will be saved to different db tables)
2. Springboot microservice having REST apis(GET,POST) with different response code(200,400,500)
3. Microservice is having two different database connectivity( Mysql & PostgresSql ).
4. Microservice is having a kafka connection as a consumer.
5. Monitoring mechanism is needed to trigger an alert in case of either of the DB connectivity goes down or kafka connectivity goes down or any api returns 500 response code.
6. Technology used are – springboot actuator, Prometheus, Grafana.
7. Dashboard is having 6 major panel (MySql Connection, PostgreSql Connection, GET api 500, POST api 500, POST 200 max response time, kafka connection)
8. MySql Connection & PostgreSql Connection value could be 0 or 1 where 0 means connection is ok and 1 means error.
9. Kafka connection value grater than 0 means it connected to brokers where 0 means no connection.
10. The alert will be trigger if either of MySql Connection/Postgres Connection value become 1 or Kafka Connection value become 0 or GET/POST(500) panel value becomes more than 0.
11. It’s also possible to monitor the max response time of /account/create api to determine if it’s more than expected average value or not then the alert can be sent for further investigation to identify the slowness of API.

### Step 1: 
Edit monitoring/prometheus.yml file and put your local ip in static_configs.target.
### Step 2:
Run command >> "mvn clean install"
### Step 3:
Run command >> "docker-compose up -d"
### Step 4:
Open Grafana in browser - http://localhost:3000 with username = admin, password = admin(skip password change option).
### Step 5:
In terminal/command prompt run command "docker ps" and copy the container id of prom/prometheus:v2.14.0 then run below command - 
docker inspect  -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "container id from above".
### Step 6:
Above command will provide an IP-Address, in Grafana browser goto Configuration > Add data source > Prometheus > in http url put "http://{ip address copied from above step}:9090" > Click "Save & Test".
### Step 7:
Now goto Dashboards > Browse > Click Import > Click upload json file > choose the grafana-dashboard.json file from monitoring folder.
### Step 8:
Now your dashboard is imported but the datasource of the dashboard needs to refresh, so click "Edit" of each panel and choose "Prometheus" in datasource dropdown and click "Run query" and "Apply".
### Step 9:
Dashboard is ready to work and to test it -  run command -> "docker ps" -> copy mysql container id and run -> "docker stop {container id}", dashboard panel of mysql connection must change to "1" and again if you run -> "docker start {container id}", panel data must become "0"
### Step 10:
To test kafka connectivity do the same like step 9 and run docker stop for kafka container id then the dashboard kafka connection panel value will become "0", that means no connection and again if you do docker start {container id} the value will increase to 1 or 2.
### Step 11:
To test /account/create(500 error) panel, import the postman collection "Account.postman_collection.json" and hit the create endpoint 2 times with same "accountNumber".