package edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class CreateSensorRequestObject {
    private String name;
    private String description;
    private JsonNode metadata;
}
