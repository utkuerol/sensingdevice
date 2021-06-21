package edu.kit.tm.cm.iot.sensingdevice.api.dto;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import lombok.Data;

@Data
public class DatastreamDTO {

    private ObservedProperty observedProperty;

    public DatastreamDTO(Datastream datastream) {
        this.observedProperty = datastream.getObservedProperty();
    }
}
