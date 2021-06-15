package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@EqualsAndHashCode
public class SensingDevice {

    private String id;

    @Setter
    private String serialNumber;

    @Setter
    private String manufacturer;

    @Setter
    private String model;

    @Getter
    private List<Sensor> sensors;

    public SensingDevice(String serialNumber, String manufacturer, String model) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.id = UUID.randomUUID().toString().toUpperCase();
    }

    public void addSensor(String name, String description, String metadata) {
        Sensor sensor = new Sensor(name, description, metadata);
        this.sensors.add(sensor);
    }

    public void removeSensor(@NonNull Sensor sensor) {
        this.sensors.remove(sensor);
    }

    public void observe(@NonNull Sensor sensor, @NonNull ObservedProperty observedProperty,
            @NonNull Observation observation) {
        if (!sensorIsAttached(sensor)) {
            throw new IllegalArgumentException("Given Sensor is not attached to the SensingDevice");
        }
        findSensor(sensor).observe(observedProperty, observation);
    }

    public List<Datastream> getObservations(@NonNull Sensor sensor) {
        if (!sensorIsAttached(sensor)) {
            throw new IllegalArgumentException("Given Sensor is not attached to the SensingDevice");
        }
        return findSensor(sensor).getDatastreams();
    }

    public Datastream getObservations(@NonNull Sensor sensor, @NonNull ObservedProperty observedProperty) {
        if (!sensorIsAttached(sensor)) {
            throw new IllegalArgumentException("Given Sensor is not attached to the SensingDevice");
        }
        return findSensor(sensor).getDatastreams().stream()
                .filter(d -> d.getObservedProperty().equals(observedProperty)).findAny().get();
    }

    public Datastream getObservations(@NonNull ObservedProperty observedProperty) {
        var combinedDatastream = new Datastream(observedProperty);
        var datastreams = this.sensors.stream()
                .flatMap(s -> s.getDatastreams().stream().filter(d -> d.getObservedProperty().equals(observedProperty)))
                .toList();
        for (Datastream datastream : datastreams) {
            combinedDatastream = combinedDatastream.join(datastream);
        }
        return combinedDatastream;
    }

    private boolean sensorIsAttached(Sensor sensor) {
        return !this.sensors.contains(sensor);
    }

    private Sensor findSensor(Sensor sensor) {
        var i = this.sensors.indexOf(sensor);
        var s = this.sensors.get(i);
        return s;
    }
}
