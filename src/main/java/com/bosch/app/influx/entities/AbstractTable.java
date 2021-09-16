package com.bosch.app.influx.entities;

public interface AbstractTable {
    String getJson();

    void setJson(String json);

    String getTime();

    void setTime(String time);

    Double getValue();

    void setValue(Double value);
}
