#### Dummy Deployment

# starts grafana with user id, pw and open standard ports e.g. amqp
```Setup Solice
    docker run -d -p 8080:8080 -p 55555:55555 -p:8008:8008 -p:1883:1883 -p:8000:8000 -p:5672:5672 -p:9000:9000 -p:2222:2222 /
    --shm-size=2g /
    --env username_admin_globalaccesslevel=admin /
    --env username_admin_password=admin /
    --name=solace solace/solace-pubsub-standard
```

# starts InfluxDB
```Setup InfluxDB
   ./influxdb/influxdb_docker_local.sh
   ./influxdb/create_local_metaDatadb.sh
   Local access:
   influx -host localhost -port 8090
   Change to measurementdb:
   use measurementdb
   Print measurement data:
   SELECT mean(value) FROM "sensordata"
```
# starts grafana with user id, pw and using the data folder
```Setup Grafana
    docker run -p 3000:3000 -v grafana:/var/lib/grafana grafana/grafana

    open localhost:3000
    username admin
    pw admin (default)
    changed : LikeABosch123
```

# starts solace with user id and pw
```Setup Solace
    docker run -d -p 8080:8080 -p 55555:55555 -p:8008:8008 -p:1883:1883 -p:8000:8000 -p:5672:5672 -p:9000:9000 -p:2222:2222
    --shm-size=2g
    --env username_admin_globalaccesslevel=admin
    --env username_admin_password=admin
    --name=solace solace/solace-pubsub-standard
```

#Build Dummy App as consumer
```Build docker image for dummy-app
    Go to the project root where Dockerfile is located
    docker build -t rd-dummyapp:0.0.1 .
    docker run': docker run -it --rm --env-file ./docker-env-settings --name radiumDummyApp rd-dummyapp:0.0.1
```
#Environment settings:
```
PORT=9000 (ENDPOINT for METRICS)
SOLACE_ENDPOINT=amqp://solace.edge.svc.cluster.local:5672 (Solace ENDPOINT)
SOLACE_QUEUE=radium3,radium2 (SOLACE_QUEUE will be configured in solace where the User has admin rights)
SOLACE_USERNAME=cornerstone
SOLACE_PASSWORD=LikeABosch123
INFLUXDB_USERNAME=admin
INFLUXDB_PASSWORD=admin
INFLUXDB_URL=http://influxdb.edge.svc.cluster.local:8086
INFLUXDB_RETENTION_POLICY=autogen
INFLUXDB_DATABASE=measurementdb (INFLUXDB_DATABASE has to be created in influxdb)
#here he have to set consumer or producer
ACTIVE_PROFILES=consumer
```
#Build Sensor App
Please check README [Sensor-APP](https://sourcecode.socialcoding.bosch.com/projects/CORNERSTONE/repos/rd-sensor/browse)