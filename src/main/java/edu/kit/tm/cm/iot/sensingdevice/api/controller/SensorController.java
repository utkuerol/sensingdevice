package edu.kit.tm.cm.iot.sensingdevice.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.tm.cm.iot.sensingdevice.api.SensorAPI;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects.CreateSensorRequestObject;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.SensorDTO;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.SensorOperations;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.InvalidSensorException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;
import lombok.SneakyThrows;

@RestController
public class SensorController implements SensorAPI {

    private static final String ERR_NO_SENSING_DEVICE_FOUND = "SensingDevice not found.";
    private static final String ERR_NO_SENSOR_FOUND = "SensingDevice not found.";
    private static final String ERR_INVALID_SENSOR = "Sensor is invalid.";

    @Autowired
    private SensorOperations sensorOperations;

    @SneakyThrows
    @Override
    public Collection<SensorDTO> getSensors(String deviceId) {
        return sensorOperations.getSensors(deviceId).stream().map(SensorDTO::new).toList();
    }

    @SneakyThrows
    @Override
    public SensorDTO createSensor(String deviceId, CreateSensorRequestObject requestObject) {
        var createdSensor = sensorOperations.addSensor(deviceId, requestObject.getName(),
                requestObject.getDescription(), requestObject.getMetadata().toString());
        return new SensorDTO(createdSensor);
    }

    @SneakyThrows
    @Override
    public SensorDTO getSensor(String deviceId, String sensorId) {
        var sensor = sensorOperations.getSensor(deviceId, sensorId);
        return new SensorDTO(sensor);
    }

    @SneakyThrows
    @Override
    public void deleteSensor(String deviceId, String sensorId) {
        sensorOperations.deleteSensor(deviceId, sensorId);
    }

    @ExceptionHandler(SensingDeviceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = SensorController.ERR_NO_SENSING_DEVICE_FOUND)
    private void handleSensingDeviceNotFoundException() {
    }

    @ExceptionHandler(SensorNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = SensorController.ERR_NO_SENSOR_FOUND)
    private void handleSensorNotFoundException() {
    }

    @ExceptionHandler(InvalidSensorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = SensorController.ERR_INVALID_SENSOR)
    private void handleInvalidSensorException() {
    }

}
