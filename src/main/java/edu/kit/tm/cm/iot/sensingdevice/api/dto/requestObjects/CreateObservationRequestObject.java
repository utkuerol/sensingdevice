package edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects;

import com.fasterxml.jackson.databind.JsonNode;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import lombok.Data;

@Data
public class CreateObservationRequestObject {
    private ObservedProperty observedProperty;
    private JsonNode value;
}
