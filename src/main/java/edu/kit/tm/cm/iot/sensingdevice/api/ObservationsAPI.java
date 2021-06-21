package edu.kit.tm.cm.iot.sensingdevice.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kit.tm.cm.iot.sensingdevice.api.dto.DatastreamObservationsDTO;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;

@RequestMapping
public interface ObservationsAPI {

    @GetMapping("/devices/{deviceId}/sensors/{sensorId}/observations")
    List<DatastreamObservationsDTO> getSensorObservations(@PathVariable String deviceId, @PathVariable String sensorId,
            @RequestParam(required = false) String from, @RequestParam(required = false) String to);

    @PostMapping("/devices/{deviceId}/sensors/{sensorId}/observations")
    List<DatastreamObservationsDTO> createSensorObservation(@PathVariable String deviceId,
            @PathVariable String sensorId, @RequestBody ObservedProperty observedProperty, @RequestBody String value);

    @GetMapping("/devices/{deviceId}/sensors/{sensorId}/observations/{observedProperty}")
    DatastreamObservationsDTO getSensorObservationsWithObservedProperty(@PathVariable String deviceId,
            @PathVariable String sensorId, @PathVariable String observedProperty,
            @RequestParam(required = false) String from, @RequestParam(required = false) String to);

    @DeleteMapping("/devices/{deviceId}/sensors/{sensorId}/observations/{observedProperty}")
    void deleteSensorObservationsWithObservedProperty(@PathVariable String deviceId, @PathVariable String sensorId,
            @PathVariable String observedProperty, @RequestParam(required = true) String from,
            @RequestParam(required = true) String to);

    @GetMapping("/devices/{deviceId}/observations/{observedProperty}")
    DatastreamObservationsDTO getObservationsWithObservedProperty(@PathVariable String deviceId,
            @PathVariable String observedProperty, @RequestParam(required = false) String from,
            @RequestParam(required = false) String to);

    @DeleteMapping("/devices/{deviceId}/observations/{observedProperty}")
    void deleteObservationsWithObservedProperty(@PathVariable String deviceId, @PathVariable String observedProperty,
            @RequestParam(required = true) String from, @RequestParam(required = true) String to);
}
