package com.bosch.app.influx;

import com.bosch.app.influx.entities.TableNames;
import com.bosch.app.influx.schema.MetaData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Component
@Configuration
@EnableConfigurationProperties(InfluxDbConfig.class)
public class InfluxDbAccessor {

    private final InfluxDB influxDb;
    private final InfluxDbConfig influxDbConfig;
    private final InfluxDBResultMapper mapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxDbAccessor.class);
    public InfluxDbAccessor(InfluxDB influxDB, InfluxDbConfig influxDbConfig) {
        this.influxDbConfig = influxDbConfig;
        this.influxDb = influxDB;
        mapper = new InfluxDBResultMapper();
        try {
            testConnection();
        } catch (Exception e) {
            LOGGER.warn("Connection to InfluxDB not working", e);
        }
    }

    private boolean testConnection() {
        var response = influxDb.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            LOGGER.error("Error pinging server.");
            return false;
        }
        return true;
    }

    private void write(String tableName, MetaData metaData, Consumer<Point.Builder> operation) {
        if (!testConnection()) {
            return;
        }

        var timestamp = ZonedDateTime.parse(metaData.getTimestamp()).toInstant().toEpochMilli();

        var tags = new HashMap<String, String>();
        tags.put("sensor", metaData.getSensor());

        var point = Point.measurement(tableName).tag(tags);
        operation.accept(point);
        point.time(timestamp, TimeUnit.MILLISECONDS);
        influxDb.write(influxDbConfig.getDatabaseName(), influxDbConfig.getRetentionPolicy(), point.build());
    }

    public void writeMeasurements(String measurement) {
        try {
            MetaData metaData = objectMapper.readValue(measurement, MetaData.class);
            write(TableNames.MEASUREMENT.getName(), metaData,
                    point -> {
                        try {

                            point.addField("value", metaData.getValue());
                            point.addField("json", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(metaData));
                        } catch (JsonProcessingException e) {
                            LOGGER.error("Error serializing dto: {}", e.getMessage(), e);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void cleanup() {
        influxDb.close();
    }


}
