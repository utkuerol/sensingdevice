package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.time.Instant;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Datastream {

    private SortedSet<Observation> observations;

    private ObservedProperty observedProperty;

    public Datastream(ObservedProperty observedProperty) {
        this.observedProperty = observedProperty;
        this.observations = new TreeSet<Observation>();
    }

    public void insert(Observation observation) {
        this.observations.add(observation);
    }

    public SortedSet<Observation> getObservations() {
        return this.observations;
    }

    public SortedSet<Observation> getObservations(Instant start, Instant end) {
        var results = this.observations.stream()
                .filter(o -> o.getTimestamp().isBefore(end) && o.getTimestamp().isAfter(start))
                .collect(Collectors.toCollection(TreeSet::new));
        return results;
    }

    public Datastream join(Datastream datastream) {
        if (!datastream.observedProperty.equals(this.observedProperty)) {
            throw new IllegalArgumentException("Datastreams dont't observe the same property");
        }
        Datastream result = new Datastream(this.observedProperty);
        SortedSet<Observation> joinedObservations = new TreeSet<Observation>();
        joinedObservations.addAll(this.observations);
        joinedObservations.addAll(datastream.observations);
        result.observations = joinedObservations;
        return result;
    }
}
