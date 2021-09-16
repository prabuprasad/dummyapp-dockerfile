package com.bosch.app.influx.entities;

public enum TableNames {
    MEASUREMENT("sensordata");

    private String name;

    TableNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
