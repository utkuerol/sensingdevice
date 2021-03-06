package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.InvalidSensingDeviceException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;

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
     * @throws SensingDeviceNotFoundException
     */
    public SensingDevice getSensingDevice(String deviceId) throws SensingDeviceNotFoundException {
        return sensingDeviceRepository.findById(deviceId).orElseThrow(() -> new SensingDeviceNotFoundException());
    }

    /**
     * Creates and returns a SensingDevice entity.
     * 
     * @param serialNumber
     * @param manufacturer
     * @param model
     * @return created SensingDevice entity
     * @throws InvalidSensingDeviceException
     */
    public SensingDevice createSensingDevice(String serialNumber, String manufacturer, String model)
            throws InvalidSensingDeviceException {
        SensingDevice sensingDevice;
        try {
            sensingDevice = new SensingDevice(serialNumber, manufacturer, model);
        } catch (Exception e) {
            throw new InvalidSensingDeviceException();
        }
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
     * @throws SensingDeviceNotFoundException
     */
    public SensingDevice updateSensingDevice(String deviceId, String serialNumber, String manufacturer, String model)
            throws SensingDeviceNotFoundException {
        var sensingDevice = sensingDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new SensingDeviceNotFoundException());
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
     * @throws SensingDeviceNotFoundException
     */
    public void deleteSensingDevice(String deviceId) throws SensingDeviceNotFoundException {
        var sensingDevice = sensingDeviceRepository.findById(deviceId)
                .orElseThrow(() -> new SensingDeviceNotFoundException());
        sensingDeviceRepository.delete(sensingDevice);
    }
}
