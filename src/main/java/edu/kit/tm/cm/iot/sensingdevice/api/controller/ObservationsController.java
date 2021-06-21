package edu.kit.tm.cm.iot.sensingdevice.api.controller;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.tm.cm.iot.sensingdevice.api.ObservationsAPI;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects.CreateObservationRequestObject;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.DatastreamObservationsDTO;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.ObservationDTO;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.ObservationOperations;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;
import lombok.SneakyThrows;

@RestController
public class ObservationsController implements ObservationsAPI {

        private static final String ERR_NO_SENSING_DEVICE_FOUND = "SensingDevice not found.";
        private static final String ERR_NO_SENSOR_FOUND = "SensingDevice not found.";

        @Autowired
        private ObservationOperations observationOperations;

        @SneakyThrows
        @Override
        public Collection<DatastreamObservationsDTO> getSensorObservations(String deviceId, String sensorId,
                        Optional<Instant> from, Optional<Instant> to) {
                return observationOperations.getObservationsOfSensor(deviceId, sensorId, from, to).stream()
                                .map(DatastreamObservationsDTO::new).toList();
        }

        @SneakyThrows
        @Override
        public ObservationDTO createSensorObservation(String deviceId, String sensorId,
                        CreateObservationRequestObject requestObject) {
                var createdObservation = observationOperations.createObservation(deviceId, sensorId,
                                requestObject.getObservedProperty(), requestObject.getValue().toString());
                return new ObservationDTO(createdObservation);
        }

        @SneakyThrows
        @Override
        public DatastreamObservationsDTO getSensorObservationsWithObservedProperty(String deviceId, String sensorId,
                        String observedProperty, Optional<Instant> from, Optional<Instant> to) {
                var datastream = observationOperations.getObservationsOfSensorWithObservedProperty(deviceId, sensorId,
                                observedProperty, from, to);
                return new DatastreamObservationsDTO(datastream);
        }

        @SneakyThrows
        @Override
        public void deleteSensorObservationsWithObservedProperty(String deviceId, String sensorId,
                        String observedProperty, Instant from, Instant to) {
                observationOperations.deleteObservationsOfSensorWithObservedProperty(deviceId, sensorId,
                                observedProperty, from, to);
        }

        @SneakyThrows
        @Override
        public DatastreamObservationsDTO getObservationsWithObservedProperty(String deviceId, String observedProperty,
                        Optional<Instant> from, Optional<Instant> to) {
                var datastream = observationOperations.getObservationsWithObservedProperty(deviceId, observedProperty,
                                from, to);
                return new DatastreamObservationsDTO(datastream);
        }

        @SneakyThrows
        @Override
        public void deleteObservationsWithObservedProperty(String deviceId, String observedProperty, Instant from,
                        Instant to) {
                observationOperations.deleteObservationsWithObservedProperty(deviceId, observedProperty, from, to);
        }

        @ExceptionHandler(SensingDeviceNotFoundException.class)
        @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ObservationsController.ERR_NO_SENSING_DEVICE_FOUND)
        private void handleSensingDeviceNotFoundException() {
        }

        @ExceptionHandler(SensorNotFoundException.class)
        @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ObservationsController.ERR_NO_SENSOR_FOUND)
        private void handleSensorNotFoundException() {
        }

}
