#!/bin/bash

echo "create local influx data base with dummy data"
influx -host localhost -port 8090 -import -path=measurementdb.txt -precision=rfc3339

