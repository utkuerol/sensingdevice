package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;

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
         * @throws SensingDeviceNotFoundException
         */
        public Sensor addSensor(String sensingDeviceId, String sensorName, String sensorDescription,
                        String sensorMetadata) throws SensingDeviceNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
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
         * @throws SensorNotFoundException
         * @throws SensingDeviceNotFoundException
         */
        public Sensor updateSensor(String sensingDeviceId, String sensorId, String sensorName, String sensorDescription,
                        String sensorMetadata) throws SensorNotFoundException, SensingDeviceNotFoundException {
                if (sensorName.equals("")) {
                        throw new IllegalArgumentException("Sensor name cannot be empty");
                }
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny()
                                .orElseThrow(() -> new SensorNotFoundException());
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
         * @throws SensingDeviceNotFoundException
         * @throws SensorNotFoundException
         */
        public void deleteSensor(String sensingDeviceId, String sensorId)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny()
                                .orElseThrow(() -> new SensorNotFoundException());
                sensingDevice.removeSensor(sensor);
                sensingDeviceRepository.update(sensingDevice);
        }

        /**
         * Gets the Sensor with the @param sensorId from the SensingDevice with
         * the @param SensingDeviceId
         * 
         * @return found sensor
         * @throws SensingDeviceNotFoundException
         * @throws SensorNotFoundException
         */
        public Sensor getSensor(String sensingDeviceId, String sensorId)
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
                                .orElseThrow(() -> new SensingDeviceNotFoundException());
                var sensor = sensingDevice.getSensors().stream().filter(s -> s.getId().equals(sensorId)).findAny()
                                .orElseThrow(() -> new SensorNotFoundException());
                return sensor;
        }
}
