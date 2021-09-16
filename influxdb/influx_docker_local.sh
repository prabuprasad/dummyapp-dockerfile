#!/bin/bash

docker run -d -p 8090:8086 --name influxdb_dev influxdb:1.5-alpine