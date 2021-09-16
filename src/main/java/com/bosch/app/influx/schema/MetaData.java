package com.bosch.app.influx.schema;

import com.bosch.app.influx.entities.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData extends AbstractEntity {
    private String sensor;
    private Double value;
    private String timestamp;
    public MetaData() {
    }

    public MetaData(Double value, String sensor,String timestamp) {
        this.value = value == null ? 0 : value;
        this.timestamp = timestamp == null ? "" : timestamp;
        this.sensor = sensor == null ? "" : sensor;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
