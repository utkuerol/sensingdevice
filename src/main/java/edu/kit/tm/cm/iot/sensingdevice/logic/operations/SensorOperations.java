package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;

@Service
@Transactional
public class SensorOperations {

    private final SensingDeviceRepository sensingDeviceRepository;

    @Autowired
    public SensorOperations(SensingDeviceRepository sensingDeviceRepository) {
        this.sensingDeviceRepository = sensingDeviceRepository;
    }

    /**
     * Adds new Sensor to the SensingDevice
     * 
     * @param sensingDeviceId
     * @param sensorName
     * @param sensorDescription
     * @param sensorMetadata
     * @return
     */
    public Sensor addSensor(String sensingDeviceId, String sensorName, String sensorDescription,
            String sensorMetadata) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).get();
        sensingDevice.addSensor(sensorName, sensorDescription, sensorMetadata);
        sensingDeviceRepository.update(sensingDevice);
        var addedSensor = sensingDevice.getSensors().get(sensingDevice.getSensors().size() - 1);
        return addedSensor;
    }

    /**
     * Updates a Sensor of a SensingDevice
     * 
     * @param sensingDeviceId
     * @param sensorId
     * @param sensorName
     * @param sensorDescription
     * @param sensorMetadata
     * @return
     */
    public Sensor updateSensor(String sensingDeviceId, String sensorId, String sensorName, String sensorDescription,
            String sensorMetadata) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).get();
        var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny().get();
        sensor.setDescription(sensorDescription);
        sensor.setName(sensorName);
        sensor.setMetadata(sensorMetadata);
        sensingDeviceRepository.update(sensingDevice);
        return sensor;
    }

    /**
     * Deletes a Sensor of a SensingDevice
     * 
     * @param sensingDeviceId
     * @param sensorId
     */
    public void deleteSensor(String sensingDeviceId, String sensorId) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).get();
        var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny().get();
        sensingDevice.removeSensor(sensor);
        sensingDeviceRepository.update(sensingDevice);
    }

    /**
     * Gets the Sensor with the @param sensorId from the SensingDevice with
     * the @param SensingDeviceId
     * 
     * @return found sensor
     */
    public Sensor getSensor(String sensingDeviceId, String sensorId) {
        var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId).get();
        var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny().get();
        return sensor;
    }
}
