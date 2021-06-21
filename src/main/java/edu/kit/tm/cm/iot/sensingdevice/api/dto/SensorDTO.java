package edu.kit.tm.cm.iot.sensingdevice.api.dto;

import java.util.List;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import lombok.Data;

@Data
public class SensorDTO {

    private String id;
    private String name;
    private String description;
    private String metadata;
    private List<DatastreamDTO> datastreams;

    public SensorDTO(Sensor sensor) {
        this.id = sensor.getId();
        this.name = sensor.getName();
        this.description = sensor.getDescription();
        this.metadata = sensor.getMetadata();
        this.datastreams = sensor.getDatastreams().stream().map(DatastreamDTO::new).toList();
    }
}
