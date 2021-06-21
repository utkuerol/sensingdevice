package edu.kit.tm.cm.iot.sensingdevice.api.dto;

import java.util.List;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import lombok.Data;

@Data
public class DatastreamObservationsDTO {

    private ObservedProperty observedProperty;
    private List<ObservationDTO> observations;

    public DatastreamObservationsDTO(Datastream datastream) {
        this.observedProperty = datastream.getObservedProperty();
        this.observations = datastream.getObservations().stream().map(ObservationDTO::new).toList();
    }
}
