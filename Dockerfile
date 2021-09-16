FROM adoptopenjdk/openjdk11

ADD target/solace-amqp-sample-0.0.1-SNAPSHOT.jar //

ENV PORT=$PORT
ENV SOLACE_ENDPOINT=$SOLACE_ENDPOINT
ENV SOLACE_QUEUE=$SOLACE_QUEUE
ENV SOLACE_USERNAME=$SOLACE_USERNAME
ENV SOLACE_PASSWORD=$SOLACE_PASSWORD

ENV INFLUXDB_USERNAME=$SINFLUXDB_USERNAME
ENV INFLUXDB_PASSWORD=$INFLUXDB_PASSWORD
ENV INFLUXDB_URL=$INFLUXDB_URL
ENV INFLUXDB_RETENTION_POLICY=$INFLUXDB_RETENTION_POLICY
ENV INFLUXDB_DATABASE=$INFLUXDB_DATABASE

ENV ACTIVE_PROFILES=$ACTIVE_PROFILES

CMD java -jar solace-amqp-sample-0.0.1-SNAPSHOT.jar \
    --server.port=$PORT \
    --solace.host=$SOLACE_ENDPOINT \
    --solace.username=$SOLACE_USERNAME \
    --solace.password=$SOLACE_PASSWORD \
    --solace.queue.list=$SOLACE_QUEUE \
    --spring.influx.url=$INFLUXDB_URL \
    --spring.influx.username=$INFLUXDB_USERNAME \
    --spring.influx.password=$INFLUXDB_PASSWORD \
    --spring.influx.databaseName=$INFLUXDB_DATABASE \
    --spring.influx.retention-policy=$INFLUXDB_RETENTION_POLICY \
    --spring.profiles.active=cloud,$ACTIVE_PROFILES \
    --management.metrics.export.influx.user-name=$INFLUXDB_USERNAME \
    --management.metrics.export.influx.password=$INFLUXDB_PASSWORD \
    --management.metrics.export.influx.uri=$INFLUXDB_URL
