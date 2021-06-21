package edu.kit.tm.cm.iot.sensingdevice.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kit.tm.cm.iot.sensingdevice.api.dto.SensingDeviceDTO;

@RequestMapping
public interface SensingDeviceAPI {

    @GetMapping("/devices")
    List<SensingDeviceDTO> getDevices();

    @PostMapping("/devices")
    SensingDeviceDTO createDevice(@RequestBody String serialNumber, @RequestBody String manufacturer,
            @RequestBody String model);

    @GetMapping("/devices/{deviceId}")
    SensingDeviceDTO getDevice(@PathVariable String deviceId);

    @DeleteMapping("/devices/{deviceId}")
    void deleteDevice(@PathVariable String deviceId);

}
