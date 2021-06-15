package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

public class SensingDeviceUnitTests {

    String serialNumber = "C02TXHZUHV29";
    String manufacturer = "SIEMENS";
    String model = "MRI";

    private static SensingDevice validSensingDevice;

    @BeforeEach
    void setUp() {
        validSensingDevice = new SensingDevice(serialNumber, manufacturer, model);
    }

    @Test
    public void sensingDevice_shouldAssignAttributesCorrectly_validInput() {
        var sensingDevice = new SensingDevice(serialNumber, manufacturer, model);
        assertNotNull(sensingDevice);
        assertDoesNotThrow(() -> UUID.fromString(sensingDevice.getId()));
        assertNotNull(sensingDevice.getSensors());
        assertEquals(sensingDevice.getSerialNumber(), serialNumber);
        assertEquals(sensingDevice.getManufacturer(), manufacturer);
        assertEquals(sensingDevice.getModel(), model);
        assertEquals(checkInvariants(sensingDevice), true);
    }

    @Test
    public void addSensor_shouldCreateAndAppendNewSensor_validInput() {
        validSensingDevice.addSensor("name", "description", "metadata");
        assertEquals(validSensingDevice.getSensors().size(), 1);
        assertEquals(validSensingDevice.getSensors().get(0).getName(), "name");
        assertEquals(validSensingDevice.getSensors().get(0).getDescription(), "description");
        assertEquals(validSensingDevice.getSensors().get(0).getMetadata(), "metadata");
        assertDoesNotThrow(() -> UUID.fromString(validSensingDevice.getSensors().get(0).getId()));
        assertNotNull(validSensingDevice.getSensors().get(0).getDatastreams());
        assertEquals(validSensingDevice.getSensors().get(0).getDatastreams().size(), 0);
        assertEquals(checkInvariants(validSensingDevice), true);
    }

    @Test
    public void addSensor_shouldThrowIllegalArgumentsException_invalidInput() {
        assertThrows(IllegalArgumentException.class, () -> validSensingDevice.addSensor("", "description", "metadata"));
        assertEquals(validSensingDevice.getSensors().size(), 0);
        assertEquals(checkInvariants(validSensingDevice), true);
    }

    private boolean checkInvariants(SensingDevice device) {
        return device.getSensors().stream().distinct().toList().size() == device.getSensors().size();
    }

}
