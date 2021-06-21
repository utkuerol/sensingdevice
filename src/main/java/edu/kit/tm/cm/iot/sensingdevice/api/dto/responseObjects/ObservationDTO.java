package edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import lombok.Data;

@Data
public class ObservationDTO {

    private JsonNode value;
    private String timestamp;

    public ObservationDTO(Observation observation) {

        // TODO not working as expected
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.value = mapper.readTree(observation.getValue());
        } catch (Exception e) {
            try {
                this.value = mapper.readTree("{\"error\":\"corrupted json\"}");
            } catch (Exception e1) {
                // ignore and let it be null
            }
        }
        this.timestamp = observation.getTimestamp().toString();
    }
}
