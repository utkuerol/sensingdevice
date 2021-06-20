package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;

public class ObservationOperations {
    private final SensingDeviceRepository sensingDeviceRepository;

    @Autowired
    public ObservationOperations(SensingDeviceRepository sensingDeviceRepository) {
        this.sensingDeviceRepository = sensingDeviceRepository;
    }

    public List<Datastream> getObservationsOfSensor(String sensingDeviceId, String sensorId, Optional<Instant> start,
            Optional<Instant> end) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var sensor = this.findSensor(sensingDeviceId, sensorId);
        var datastreams = sensingDevice.getObservations(sensor);
        datastreams = datastreams.stream().map(d -> this.applyTimeFilter(d, start, end)).collect(Collectors.toList());
        return datastreams;
    }

    public Datastream getObservationsOfSensorWithObservedProperty(String sensingDeviceId, String sensorId,
            String observedPropertyName, Optional<Instant> start, Optional<Instant> end) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var sensor = this.findSensor(sensingDeviceId, sensorId);
        var datastream = sensingDevice.getObservations(sensor, new ObservedProperty(observedPropertyName, "", ""));
        return applyTimeFilter(datastream, start, end);
    }

    public Datastream getObservationsWithObservedProperty(String sensingDeviceId, String observedPropertyName,
            Optional<Instant> start, Optional<Instant> end) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var datastream = sensingDevice.getObservations(new ObservedProperty(observedPropertyName, "", ""));
        return applyTimeFilter(datastream, start, end);
    }

    public Observation createObservation(String sensingDeviceId, String sensorId, ObservedProperty observedProperty,
            String observedValue) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var sensor = this.findSensor(sensingDeviceId, sensorId);
        var observation = new Observation(observedValue);
        sensingDevice.observe(sensor, observedProperty, observation);
        sensingDeviceRepository.update(sensingDevice);
        return observation;
    }

    public void deleteObservationsOfSensorWithObservedProperty(String sensingDeviceId, String sensorId,
            String observedPropertyName, Instant start, Instant end) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var sensor = this.findSensor(sensingDeviceId, sensorId);
        var datastream = sensingDevice.getObservations(sensor, new ObservedProperty(observedPropertyName, "", ""));
        datastream = this.removeObservationsInTimeFilter(datastream, start, end);
        sensingDeviceRepository.update(sensingDevice);
    }

    public void deleteObservationsWithObservedProperty(String sensingDeviceId, String observedPropertyName,
            Instant start, Instant end) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var datastreams = sensingDevice.getSensors().stream()
                .flatMap(s -> s.getDatastreams().stream()
                        .filter(d -> d.getObservedProperty().getName().equals(observedPropertyName)))
                .collect(Collectors.toList());
        datastreams.forEach(d -> this.removeObservationsInTimeFilter(d, start, end));
        sensingDeviceRepository.update(sensingDevice);
    }

    private Datastream applyTimeFilter(Datastream datastream, Optional<Instant> start, Optional<Instant> end) {
        datastream.setObservations(datastream.getObservations(start.orElse(Instant.MIN), end.orElse(Instant.now())));
        return datastream;
    }

    private Datastream removeObservationsInTimeFilter(Datastream datastream, Instant start, Instant end) {
        datastream.getObservations().stream()
                .dropWhile(o -> o.getTimestamp().isBefore(end) && o.getTimestamp().isAfter(start));
        return datastream;
    }

    private Sensor findSensor(String sensingDeviceId, String sensorId) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).orElseThrow();
        var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny()
                .orElseThrow();
        return sensor;
    }

}
