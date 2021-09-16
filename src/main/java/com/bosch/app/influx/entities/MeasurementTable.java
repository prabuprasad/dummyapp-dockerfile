package com.bosch.app.influx.entities;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

@Measurement(name = "sensorMeasurement")
public class MeasurementTable implements AbstractTable {
    @Column(name = "id")
    public String id;
    @Column(name = "json")
    public String json;
    @Column(name = "time")
    public String time;
    @Column(name = "value")
    public Double value;

    public String getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
