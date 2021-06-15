package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class Sensor {

    private String id;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private String metadata;

    private List<Datastream> datastreams;

    public Sensor(String name, String description, String metadata) {
        this.name = name;
        this.description = description;
        this.metadata = metadata;
        this.id = UUID.randomUUID().toString().toUpperCase();
        this.datastreams = new ArrayList<>();
    }

    public void observe(ObservedProperty observedProperty, Observation observation) {
        var existingDatastreams = this.datastreams.stream()
                .filter(d -> d.getObservedProperty().equals(observedProperty));
        if (existingDatastreams.count() > 1) {
            throw new IllegalStateException(
                    "This sensor should not have had more than one datastream with the same observed property");
        } else if (existingDatastreams.count() == 1) {
            var d = existingDatastreams.toList().get(0);
            d.insert(observation);
        } else {
            var d = new Datastream(observedProperty);
            d.insert(observation);
            this.datastreams.add(d);
        }
    }

}
