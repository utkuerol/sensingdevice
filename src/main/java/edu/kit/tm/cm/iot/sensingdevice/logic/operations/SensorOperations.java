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

    public Sensor addSensor(String deviceId, String sensorName, String sensorDescription, String sensorMetadata) {
        var sensingDevice = sensingDeviceRepository.findById(deviceId).get();
        sensingDevice.addSensor(sensorName, sensorDescription, sensorMetadata);
        sensingDeviceRepository.update(sensingDevice);
        var addedSensor = sensingDevice.getSensors().get(sensingDevice.getSensors().size() - 1);
        return addedSensor;
    }
}
