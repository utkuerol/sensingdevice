package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.time.Instant;

import lombok.Getter;

@Getter
public class Observation implements Comparable<Observation> {

    private String value;
    private Instant timestamp;

    public Observation(String value) {
        this.value = value;
        this.timestamp = Instant.now();
    }

    @Override
    public int compareTo(Observation o) {
        return this.timestamp.compareTo(o.getTimestamp());
    }
}
