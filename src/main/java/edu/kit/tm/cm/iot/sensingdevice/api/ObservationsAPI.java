package edu.kit.tm.cm.iot.sensingdevice.api;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects.CreateObservationRequestObject;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.DatastreamObservationsDTO;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.ObservationDTO;

@RequestMapping
public interface ObservationsAPI {

        @GetMapping("/devices/{deviceId}/sensors/{sensorId}/observations")
        @ResponseStatus(HttpStatus.OK)
        Collection<DatastreamObservationsDTO> getSensorObservations(@PathVariable String deviceId,
                        @PathVariable String sensorId, @RequestParam Optional<Instant> from,
                        @RequestParam Optional<Instant> to);

        @PostMapping("/devices/{deviceId}/sensors/{sensorId}/observations")
        @ResponseStatus(HttpStatus.CREATED)
        ObservationDTO createSensorObservation(@PathVariable String deviceId, @PathVariable String sensorId,
                        @RequestBody CreateObservationRequestObject requestObject);

        @GetMapping("/devices/{deviceId}/sensors/{sensorId}/observations/{observedProperty}")
        @ResponseStatus(HttpStatus.OK)
        DatastreamObservationsDTO getSensorObservationsWithObservedProperty(@PathVariable String deviceId,
                        @PathVariable String sensorId, @PathVariable String observedProperty,
                        @RequestParam Optional<Instant> from, @RequestParam Optional<Instant> to);

        @DeleteMapping("/devices/{deviceId}/sensors/{sensorId}/observations/{observedProperty}")
        @ResponseStatus(HttpStatus.OK)
        void deleteSensorObservationsWithObservedProperty(@PathVariable String deviceId, @PathVariable String sensorId,
                        @PathVariable String observedProperty, @RequestParam Instant from, @RequestParam Instant to);

        @GetMapping("/devices/{deviceId}/observations/{observedProperty}")
        @ResponseStatus(HttpStatus.OK)
        DatastreamObservationsDTO getObservationsWithObservedProperty(@PathVariable String deviceId,
                        @PathVariable String observedProperty, @RequestParam Optional<Instant> from,
                        @RequestParam Optional<Instant> to);

        @DeleteMapping("/devices/{deviceId}/observations/{observedProperty}")
        @ResponseStatus(HttpStatus.OK)
        void deleteObservationsWithObservedProperty(@PathVariable String deviceId,
                        @PathVariable String observedProperty, @RequestParam Instant from, @RequestParam Instant to);
}
