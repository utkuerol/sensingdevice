package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.InvalidSensorException;
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
	 * @throws InvalidSensorException
	 */
	public Sensor addSensor(String sensingDeviceId, String sensorName, String sensorDescription, String sensorMetadata)
			throws SensingDeviceNotFoundException, InvalidSensorException {
		var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
				.orElseThrow(() -> new SensingDeviceNotFoundException());
		try {
			sensingDevice.addSensor(sensorName, sensorDescription, sensorMetadata);
		} catch (Exception e) {
			throw new InvalidSensorException();
		}
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
	 * @throws InvalidSensorException
	 */
	public Sensor updateSensor(String sensingDeviceId, String sensorId, String sensorName, String sensorDescription,
			String sensorMetadata)
			throws SensorNotFoundException, SensingDeviceNotFoundException, InvalidSensorException {
		if (sensorName.equals("")) {
			throw new InvalidSensorException();
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

	/**
	 * Gets all Sensors of a SensingDevice
	 * 
	 * @param sensingDeviceId
	 * @return
	 * @throws SensingDeviceNotFoundException
	 */
	public List<Sensor> getSensors(String sensingDeviceId) throws SensingDeviceNotFoundException {
		var sensingDevice = sensingDeviceRepository.findById(sensingDeviceId)
				.orElseThrow(() -> new SensingDeviceNotFoundException());
		return sensingDevice.getSensors();
	}
}
