package edu.kit.tm.cm.iot.sensingdevice.api.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.tm.cm.iot.sensingdevice.api.SensingDeviceAPI;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.SensingDeviceDTO;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.SensingDeviceOperations;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.InvalidSensingDeviceException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import lombok.SneakyThrows;

@RestController
public class SensingDeviceController implements SensingDeviceAPI {

    private static final String ERR_INVALID_SENSING_DEVICE = "SensingDevice is invalid.";
    private static final String ERR_NO_SENSING_DEVICE_FOUND = "SensingDevice not found.";

    @Autowired
    private SensingDeviceOperations sensingDeviceOperations;

    @Override
    public Collection<SensingDeviceDTO> getDevices() {
        return sensingDeviceOperations.getAllSensingDevices().stream().map(SensingDeviceDTO::new).toList();
    }

    @SneakyThrows
    @Override
    public SensingDeviceDTO createDevice(String serialNumber, String manufacturer, String model) {
        var createdDevice = sensingDeviceOperations.createSensingDevice(serialNumber, manufacturer, model);
        return new SensingDeviceDTO(createdDevice);
    }

    @SneakyThrows
    @Override
    public SensingDeviceDTO getDevice(String deviceId) {
        var device = sensingDeviceOperations.getSensingDevice(deviceId);
        return new SensingDeviceDTO(device);
    }

    @SneakyThrows
    @Override
    public void deleteDevice(String deviceId) {
        sensingDeviceOperations.deleteSensingDevice(deviceId);
    }

    @ExceptionHandler(SensingDeviceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = SensingDeviceController.ERR_NO_SENSING_DEVICE_FOUND)
    private void handleNotFoundException() {
    }

    @ExceptionHandler(InvalidSensingDeviceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = SensingDeviceController.ERR_INVALID_SENSING_DEVICE)
    private void handleInvalidSensingDeviceException() {
    }

}
