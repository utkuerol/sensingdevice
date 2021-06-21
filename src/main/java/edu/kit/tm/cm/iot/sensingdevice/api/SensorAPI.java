package edu.kit.tm.cm.iot.sensingdevice.api;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.kit.tm.cm.iot.sensingdevice.api.dto.SensorDTO;

@RequestMapping
public interface SensorAPI {

    @GetMapping("/devices/{deviceId}/sensors")
    @ResponseStatus(HttpStatus.OK)
    Collection<SensorDTO> getSensors(@PathVariable String deviceId);

    @PostMapping("/devices/{deviceId}/sensors")
    @ResponseStatus(HttpStatus.CREATED)
    SensorDTO createSensor(@PathVariable String deviceId, @RequestBody String name, @RequestBody String description,
            @RequestBody String metadata);

    @GetMapping("/devices/{deviceId}/sensors/{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    SensorDTO getSensor(@PathVariable String deviceId, @PathVariable String sensorId);

    @DeleteMapping("/devices/{deviceId}/sensors/{sensorId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteSensor(@PathVariable String deviceId, @PathVariable String sensorId);
}
