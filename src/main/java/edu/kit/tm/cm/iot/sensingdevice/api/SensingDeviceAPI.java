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

import edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects.CreateSensingDeviceRequestObject;
import edu.kit.tm.cm.iot.sensingdevice.api.dto.responseObjects.SensingDeviceDTO;

@RequestMapping
public interface SensingDeviceAPI {

    @GetMapping("/devices")
    @ResponseStatus(HttpStatus.OK)
    Collection<SensingDeviceDTO> getDevices();

    @PostMapping("/devices")
    @ResponseStatus(HttpStatus.CREATED)
    SensingDeviceDTO createDevice(@RequestBody CreateSensingDeviceRequestObject requestObject);

    @GetMapping("/devices/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    SensingDeviceDTO getDevice(@PathVariable String deviceId);

    @DeleteMapping("/devices/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteDevice(@PathVariable String deviceId);

}
