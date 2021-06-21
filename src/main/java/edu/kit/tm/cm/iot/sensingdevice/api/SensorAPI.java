package edu.kit.tm.cm.iot.sensingdevice.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.tm.cm.iot.sensingdevice.api.dto.SensorDTO;

@RequestMapping
public interface SensorAPI {

    @GetMapping("/devices/{deviceId}/sensors")
    List<SensorDTO> getSensors(@PathVariable String deviceId);

    @PostMapping("/devices/{deviceId}/sensors")
    SensorDTO createSensor(@PathVariable String deviceId);

    @GetMapping("/devices/{deviceId}/sensors/{sensorId}")
    SensorDTO getSensor(@PathVariable String deviceId, @PathVariable String sensorId);

    @DeleteMapping("/devices/{deviceId}/sensors/{sensorId}")
    SensorDTO deleteSensor(@PathVariable String deviceId, @PathVariable String sensorId);
}
