package edu.kit.tm.cm.iot.sensingdevice.api.dto.requestObjects;

import lombok.Data;

@Data
public class CreateSensingDeviceRequestObject {
    private String serialNumber;
    private String manufacturer;
    private String model;
}
