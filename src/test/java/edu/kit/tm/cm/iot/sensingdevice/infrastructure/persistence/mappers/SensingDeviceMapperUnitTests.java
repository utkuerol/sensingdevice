package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensingDevicePersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensorPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;

public class SensingDeviceMapperUnitTests {
    private static SensingDevice sensingDevice;
    private static SensingDevicePersistenceEntity sensingDevicePE;

    @BeforeEach
    void setUp() {
        var sensors = Stream
                .of(new Sensor("id1", "name", "description", "metadata", new LinkedList<>()),
                        new Sensor("id2", "name", "description", "metadata", new LinkedList<>()))
                .collect(Collectors.toList());
        var sensorsPE = Stream
                .of(new SensorPersistenceEntity(0, "id1", "name", "description", "metadata", new LinkedList<>()),
                        new SensorPersistenceEntity(0, "id2", "name", "description", "metadata", new LinkedList<>()))
                .collect(Collectors.toList());

        sensingDevice = new SensingDevice("id", "serialNumber", "manufacturer", "model", sensors);
        sensingDevicePE = new SensingDevicePersistenceEntity(0, "id", "serialNumber", "manufacturer", "model",
                sensorsPE);
    }

    @Test
    public void toPersistenceEntity() {
        var pe = SensingDeviceMapper.toPersistenceEntity(sensingDevice);
        assertEquals(sensingDevicePE.getId(), pe.getId());
        assertEquals(sensingDevicePE.getManufacturer(), pe.getManufacturer());
        assertEquals(sensingDevicePE.getModel(), pe.getModel());
        assertEquals(sensingDevicePE.getSerialNumber(), pe.getSerialNumber());
        assertEquals(2, sensingDevicePE.getSensors().size());
        assertTrue(sensingDevicePE.getSensors().containsAll(pe.getSensors()));
        assertTrue(pe.getSensors().containsAll(sensingDevicePE.getSensors()));
    }

    @Test
    public void fromPersistenceEntity() {
        assertEquals(sensingDevice, SensingDeviceMapper.fromPersistenceEntity(sensingDevicePE));
        assertEquals(2, sensingDevice.getSensors().size());
    }
}
