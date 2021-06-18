package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;

@Service
@Transactional
public class SensingDeviceOperations {

    private final SensingDeviceRepository sensingDeviceRepository;

    @Autowired
    public SensingDeviceOperations(SensingDeviceRepository sensingDeviceRepository) {
        this.sensingDeviceRepository = sensingDeviceRepository;
    }

    public List<SensingDevice> getAllSensingDevices() {
        return new LinkedList<>(sensingDeviceRepository.findAll());
    }

    /**
     * Gets the SensingDevice with the given deviceId
     * 
     * @param deviceId
     * @return SensingDevice with the deviceId
     */
    public SensingDevice getSensingDevice(String deviceId) {
        return sensingDeviceRepository.findById(deviceId).get();
    }

    /**
     * Creates and returns a SensingDevice entity.
     * 
     * @param serialNumber
     * @param manufacturer
     * @param model
     * @return created SensingDevice entity
     */
    public SensingDevice createSensingDevice(String serialNumber, String manufacturer, String model) {
        var sensingDevice = new SensingDevice(serialNumber, manufacturer, model);
        sensingDeviceRepository.create(sensingDevice);
        return sensingDevice;
    }

    /**
     * Updates the SensingDevice and returns the updated entity.
     * 
     * @param deviceId
     * @param serialNumber
     * @param manufacturer
     * @param model
     * @return updated SensingDevice entity
     */
    public SensingDevice updateSensingDevice(String deviceId, String serialNumber, String manufacturer, String model) {
        var sensingDevice = sensingDeviceRepository.findById(deviceId).get();
        sensingDevice.setSerialNumber(serialNumber);
        sensingDevice.setModel(model);
        sensingDevice.setManufacturer(manufacturer);
        sensingDeviceRepository.update(sensingDevice);
        return sensingDevice;
    }

    /**
     * Deletes the SensingDevice with the given deviceId
     * 
     * @param deviceId
     */
    public void deleteSensingDevice(String deviceId) {
        var sensingDevice = sensingDeviceRepository.findById(deviceId).get();
        sensingDeviceRepository.delete(sensingDevice);
    }

}
