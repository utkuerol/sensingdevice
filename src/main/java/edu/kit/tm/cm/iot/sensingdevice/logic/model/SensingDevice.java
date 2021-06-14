package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import lombok.Getter;
import lombok.Setter;

public class SensingDevice {

    @Getter
    @Setter
    private String serialNumber;

    @Getter
    @Setter
    private String manufacturer;

    @Getter
    @Setter
    private String model;

    public SensingDevice(String serialNumber, String manufacturer, String model) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.model = model;
    }

}
