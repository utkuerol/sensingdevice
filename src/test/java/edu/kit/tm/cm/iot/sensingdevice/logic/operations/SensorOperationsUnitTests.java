package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories.SensingDeviceRepositoryImpl;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;

public class SensorOperationsUnitTests {

    private static SensingDeviceRepository mockRepository = mock(SensingDeviceRepositoryImpl.class);
    private static SensorOperations sensorOperations = new SensorOperations(mockRepository);
    private static SensingDevice sensingDevice;
    private static Sensor sensor;

    @BeforeEach
    void setUp() {
        sensor = new Sensor("name", "description", "metadata");
        sensingDevice = new SensingDevice("serialNumber", "manufacturer", "model");
    }

    @Test
    public void addSensor() {
        var id = sensingDevice.getId();
        when(mockRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(Exception.class,
                () -> sensorOperations.addSensor("id", "sensorName", "sensorDescription", "sensorMetadata"));
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertAll(() -> sensorOperations.addSensor(id, "sensorName", "sensorDescription", "sensorMetadata"));
        assertThrows(IllegalArgumentException.class,
                () -> sensorOperations.addSensor(id, "", "sensorDescription", "sensorMetadata"));
    }

    @Test
    public void updateSensor_validInput() {
        var id = sensingDevice.getId();
        sensingDevice.getSensors().add(sensor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertAll(() -> sensorOperations.updateSensor(id, sensor.getId(), "sensorName", "sensorDescription",
                "sensorMetadata"));
    }

    @Test
    public void updateSensor_invalidInput() {
        var id = sensingDevice.getId();
        // sensor exists
        sensingDevice.getSensors().add(sensor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));

        // name must not be empty
        assertThrows(IllegalArgumentException.class,
                () -> sensorOperations.updateSensor(id, "id", "", "sensorDescription", "sensorMetadata"));

        // sensor does not exist
        sensingDevice.getSensors().remove(sensor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertThrows(SensorNotFoundException.class,
                () -> sensorOperations.updateSensor(id, "id", "sensorName", "sensorDescription", "sensorMetadata"));
    }

    @Test
    public void deleteSensor_validInput() {
        var id = sensingDevice.getId();
        sensingDevice.getSensors().add(sensor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertAll(() -> sensorOperations.deleteSensor(id, sensor.getId()));
    }

    @Test
    public void deleteSensor_invalidInput() {
        var id = sensingDevice.getId();
        // sensor does not exist
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertThrows(SensorNotFoundException.class, () -> sensorOperations.deleteSensor(id, "id"));
    }

    @Test
    public void getSensor() throws SensingDeviceNotFoundException, SensorNotFoundException {
        var id = sensingDevice.getId();
        sensingDevice.getSensors().add(sensor);
        when(mockRepository.findById(id)).thenReturn(Optional.of(sensingDevice));
        assertAll(() -> sensorOperations.getSensor(id, sensor.getId()));
        assertEquals(sensor, sensorOperations.getSensor(id, sensor.getId()));
    }
}
