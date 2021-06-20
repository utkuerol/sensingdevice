package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories.SensingDeviceRepositoryImpl;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;

public class SensingDeviceOperationsUnitTests {

        private static SensingDeviceRepository mockRepository = mock(SensingDeviceRepositoryImpl.class);
        private static SensingDeviceOperations sensingDeviceOperations = new SensingDeviceOperations(mockRepository);

        private static SensingDevice sensingDevice;

        @BeforeEach
        void setUp() {
                var sensors = Stream.of(new Sensor("id1", "name", "description", "metadata", new LinkedList<>()),
                                new Sensor("id2", "name", "description", "metadata", new LinkedList<>()))
                                .collect(Collectors.toList());

                sensingDevice = new SensingDevice("id", "serialNumber", "manufacturer", "model", sensors);
        }

        @Test
        public void getSensingDevice() throws SensingDeviceNotFoundException {
                when(mockRepository.findById("id")).thenReturn(Optional.of(sensingDevice));
                assertEquals(sensingDevice, sensingDeviceOperations.getSensingDevice("id"));
                when(mockRepository.findById("id")).thenReturn(Optional.empty());
                assertThrows(Exception.class, () -> sensingDeviceOperations.getSensingDevice("id"));
        }

        @Test
        public void getAllSensingDevices() {
                when(mockRepository.findAll()).thenReturn(new ArrayList<SensingDevice>());
                assertEquals(new ArrayList<SensingDevice>(), sensingDeviceOperations.getAllSensingDevices());
                var devices = new ArrayList<SensingDevice>();
                devices.add(sensingDevice);
                when(mockRepository.findAll()).thenReturn(devices);
                assertEquals(devices, sensingDeviceOperations.getAllSensingDevices());
        }

        @Test
        public void createSensingDevice() {
                assertEquals("serialNumber", sensingDeviceOperations
                                .createSensingDevice("serialNumber", "manufacturer", "model").getSerialNumber());
                assertEquals("manufacturer", sensingDeviceOperations
                                .createSensingDevice("serialNumber", "manufacturer", "model").getManufacturer());
                assertEquals("model", sensingDeviceOperations
                                .createSensingDevice("serialNumber", "manufacturer", "model").getModel());
        }

        @Test
        public void updateSensingDevice() throws SensingDeviceNotFoundException {
                when(mockRepository.findById("id")).thenReturn(Optional.of(sensingDevice));
                assertEquals("serialNumber2", sensingDeviceOperations
                                .updateSensingDevice("id", "serialNumber2", "manufacturer", "model").getSerialNumber());
                assertEquals("manufacturer2", sensingDeviceOperations
                                .updateSensingDevice("id", "serialNumber", "manufacturer2", "model").getManufacturer());
                assertEquals("model2", sensingDeviceOperations
                                .updateSensingDevice("id", "serialNumber", "manufacturer", "model2").getModel());
        }

}
