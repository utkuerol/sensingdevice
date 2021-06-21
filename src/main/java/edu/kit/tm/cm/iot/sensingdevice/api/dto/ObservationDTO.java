package edu.kit.tm.cm.iot.sensingdevice.api.dto;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import lombok.Data;

@Data
public class ObservationDTO {

    private String value;
    private String timestamp;

    public ObservationDTO(Observation observation) {
        this.value = observation.getValue();
        this.timestamp = observation.getTimestamp().toString();
    }
}
