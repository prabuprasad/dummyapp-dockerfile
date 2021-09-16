package com.bosch.app.influx.entities;

import com.bosch.app.influx.InfluxDbAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractEntity {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxDbAccessor.class);

    private ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();

    /**
     * Serializes the object as a JSON string.
     *
     * @return JSON representation of the object
     */
    @Override
    public final String toString() {
        try {
            return objectWriter.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error during serialization: {}", e.getMessage(), e);
            return null;
        }
    }

}
