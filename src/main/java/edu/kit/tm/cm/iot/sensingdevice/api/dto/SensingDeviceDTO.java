package edu.kit.tm.cm.iot.sensingdevice.api.dto;

import java.util.List;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import lombok.Data;

@Data
public class SensingDeviceDTO {
    private String id;
    private String serialNumber;
    private String manufacturer;
    private String model;
    private List<SensorDTO> sensors;

    public SensingDeviceDTO(SensingDevice sensingDevice) {
        this.id = sensingDevice.getId();
        this.serialNumber = sensingDevice.getSerialNumber();
        this.manufacturer = sensingDevice.getManufacturer();
        this.model = sensingDevice.getModel();
        this.sensors = sensingDevice.getSensors().stream().map(SensorDTO::new).toList();
    }
}
