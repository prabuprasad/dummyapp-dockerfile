# Dummy App

This repository contains the a test application to test solace, influx and grafana services.
- Dummy App (Spring-Boot Applicaion as Consumer)
- Dummy Sensor [GoLang Application as Producer] (https://sourcecode.socialcoding.bosch.com/projects/CORNERSTONE/repos/rd-sensor/browse))
- InfluxDB (DB service for measurement data and metrics)
- Solice (pubsub+ Event Broker)
- Grafana

The Dummy-Application consists of a Spring-Boot (consumer/producer) application sends or consumes data from solace and
store the received data into influx db.
The data stored in the InfluxDB is visualised in Grafana.

## Resources

- [Development](wiki/development.md)
- [Build](wiki/build.md)

