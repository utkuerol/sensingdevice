package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;
import lombok.NonNull;

public class ObservationOperations {
        private final SensingDeviceRepository sensingDeviceRepository;

        @Autowired
        public ObservationOperations(SensingDeviceRepository sensingDeviceRepository) {
                this.sensingDeviceRepository = sensingDeviceRepository;
        }

        public List<Datastream> getObservationsOfSensor(String sensingDeviceId, String sensorId,
                        Optional<Instant> start, Optional<Instant> end)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = this.findSensor(sensingDeviceId, sensorId);
                var datastreams = sensingDevice.getObservations(sensor);
                var filteredDatastreams = datastreams.stream().map(d -> this.deepCopyDatastream(d))
                                .collect(Collectors.toList());
                filteredDatastreams = filteredDatastreams.stream().map(d -> this.applyTimeFilter(d, start, end))
                                .collect(Collectors.toCollection(ArrayList::new));
                return filteredDatastreams;
        }

        public Datastream getObservationsOfSensorWithObservedProperty(String sensingDeviceId, String sensorId,
                        String observedPropertyName, @NonNull Optional<Instant> start, @NonNull Optional<Instant> end)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = this.findSensor(sensingDeviceId, sensorId);
                var datastream = sensingDevice.getObservations(sensor,
                                new ObservedProperty(observedPropertyName, "", ""));
                var filteredDatastream = this.deepCopyDatastream(datastream);
                filteredDatastream = this.applyTimeFilter(filteredDatastream, start, end);
                return filteredDatastream;
        }

        public Datastream getObservationsWithObservedProperty(String sensingDeviceId, String observedPropertyName,
                        @NonNull Optional<Instant> start, @NonNull Optional<Instant> end)
                        throws SensingDeviceNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var datastream = sensingDevice.getObservations(new ObservedProperty(observedPropertyName, "", ""));
                var filteredDatastream = this.deepCopyDatastream(datastream);
                filteredDatastream = this.applyTimeFilter(filteredDatastream, start, end);
                return filteredDatastream;
        }

        public Observation createObservation(String sensingDeviceId, String sensorId, ObservedProperty observedProperty,
                        String observedValue) throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = this.findSensor(sensingDeviceId, sensorId);
                var observation = new Observation(observedValue);
                sensingDevice.observe(sensor, observedProperty, observation);
                sensingDeviceRepository.update(sensingDevice);
                return observation;
        }

        public void deleteObservationsOfSensorWithObservedProperty(String sensingDeviceId, String sensorId,
                        String observedPropertyName, @NonNull Instant start, @NonNull Instant end)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = this.findSensor(sensingDeviceId, sensorId);
                var datastream = sensingDevice.getObservations(sensor,
                                new ObservedProperty(observedPropertyName, "", ""));
                datastream = this.removeObservationsInTimeFilter(datastream, start, end);
                sensingDeviceRepository.update(sensingDevice);
        }

        public void deleteObservationsWithObservedProperty(String sensingDeviceId, String observedPropertyName,
                        @NonNull Instant start, @NonNull Instant end) throws SensingDeviceNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var datastreams = sensingDevice.getSensors().stream()
                                .flatMap(s -> s.getDatastreams().stream().filter(
                                                d -> d.getObservedProperty().getName().equals(observedPropertyName)))
                                .collect(Collectors.toList());
                datastreams.forEach(d -> this.removeObservationsInTimeFilter(d, start, end));
                sensingDeviceRepository.update(sensingDevice);
        }

        private Datastream applyTimeFilter(@NonNull Datastream datastream, @NonNull Optional<Instant> start,
                        @NonNull Optional<Instant> end) {
                datastream.setObservations(
                                datastream.getObservations(start.orElse(Instant.MIN), end.orElse(Instant.now())));
                return datastream;
        }

        private Datastream removeObservationsInTimeFilter(@NonNull Datastream datastream, @NonNull Instant start,
                        @NonNull Instant end) {
                datastream.getObservations().stream()
                                .dropWhile(o -> o.getTimestamp().isBefore(end) && o.getTimestamp().isAfter(start));
                return datastream;
        }

        private Sensor findSensor(String sensingDeviceId, String sensorId)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny()
                                .orElseThrow(() -> new SensorNotFoundException());
                return sensor;
        }

        private Datastream deepCopyDatastream(Datastream datastream) {
                var filteredDatastream = new Datastream(datastream.getObservedProperty());
                var observations = new TreeSet<Observation>();
                datastream.getObservations().forEach(o -> observations.add(o));
                filteredDatastream.setObservations(observations);
                return filteredDatastream;
        }
}
