package edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import lombok.Data;

@Data
public class SensorDTO {

    private String id;
    private String name;
    private String description;
    private JsonNode metadata;
    private List<DatastreamDTO> datastreams;

    public SensorDTO(Sensor sensor) {
        this.id = sensor.getId();
        this.name = sensor.getName();
        this.description = sensor.getDescription();
        this.datastreams = sensor.getDatastreams().stream().map(DatastreamDTO::new).toList();

        // TODO not working as expected
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.metadata = mapper.readTree(sensor.getMetadata());
        } catch (Exception e) {
            try {
                this.metadata = mapper.readTree("{\"error\":\"corrupted json\"}");
            } catch (Exception e1) {
                // ignore and let it be null
            }
        }
    }
}
