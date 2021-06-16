package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensingDevice {

    private final String id;

    private String serialNumber;

    private String manufacturer;

    private String model;

    private List<Sensor> sensors;

    public SensingDevice(String serialNumber, String manufacturer, String model) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.id = UUID.randomUUID().toString().toUpperCase();
        this.sensors = new ArrayList<Sensor>();
    }

    public void addSensor(String name, String description, String metadata) {
        if (name == "") {
            throw new IllegalArgumentException("Name of a Sensor cannot be empty");
        }
        Sensor sensor = new Sensor(name, description, metadata);
        this.sensors.add(sensor);
    }

    public void removeSensor(@NonNull Sensor sensor) {
        if (!sensorIsAttached(sensor)) {
            throw new IllegalArgumentException("Given Sensor is not attached to the SensingDevice");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SensingDevice) {
            var device = (SensingDevice) obj;
            if (device.getId().equals(this.getId())) {
                return true;
            }
        }
        return false;
    }

    private boolean sensorIsAttached(Sensor sensor) {
        return this.sensors.contains(sensor);
    }

    private Sensor findSensor(Sensor sensor) {
        var i = this.sensors.indexOf(sensor);
        var s = this.sensors.get(i);
        return s;
    }

}
