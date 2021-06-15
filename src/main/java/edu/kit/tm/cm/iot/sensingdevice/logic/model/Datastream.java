package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class Datastream {

    private List<Observation> observations;

    @Getter
    private ObservedProperty observedProperty;

    public Datastream(ObservedProperty observedProperty) {
        this.observedProperty = observedProperty;
        this.observations = new ArrayList<>();
    }

    public void insert(Observation observation) {
        this.observations.add(observation);
    }

    public List<Observation> getObservations() {
        return this.observations;
    }

    public List<Observation> getObservations(Instant start, Instant end) {
        List<Observation> results = this.observations.stream()
                .filter(o -> o.getTimestamp().isBefore(end) && o.getTimestamp().isAfter(start)).toList();
        return results;
    }

    public Datastream join(Datastream datastream) {
        if (!datastream.observedProperty.equals(this.observedProperty)) {
            throw new IllegalArgumentException("Datastreams dont't observe the same property");
        }
        Datastream result = new Datastream(this.observedProperty);
        List<Observation> joinedObservations = new ArrayList<Observation>();
        joinedObservations.addAll(this.observations);
        joinedObservations.addAll(datastream.observations);
        Collections.sort(joinedObservations);
        Collections.reverse(joinedObservations);
        result.observations = joinedObservations;
        return result;
    }
}
