package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class SensingDeviceUnitTests {

    private static SensingDevice validSensingDevice;
    private static Sensor locationSensor;
    private static Sensor locationSensor2;
    private static Sensor temperatureSensor;
    private static Sensor aggregateSensor;
    private static List<Sensor> sensors;
    private static ObservedProperty location = new ObservedProperty("location", "description", "unitOfMeasurement");
    private static ObservedProperty temperature = new ObservedProperty("temperature", "description",
            "unitOfMeasurement");
    private static Datastream locationDS;
    private static Datastream locationDS2;
    private static Datastream temperatureDS;

    @BeforeEach
    void setUp() {
        String serialNumber = "C02TXHZUHV29";
        String manufacturer = "SIEMENS";
        String model = "MRI";
        validSensingDevice = new SensingDevice(serialNumber, manufacturer, model);
        sensors = new ArrayList<Sensor>();
        locationSensor = new Sensor("locationSensor", "description", "metadata");
        locationSensor2 = new Sensor("locationSensor", "description", "metadata");
        temperatureSensor = new Sensor("temperatureSensor", "description", "metadata");
        aggregateSensor = new Sensor("aggregateSensor", "description", "metadata");
        locationDS = new Datastream(location);
        locationDS2 = new Datastream(location);
        temperatureDS = new Datastream(temperature);
    }

    @Test
    public void sensingDevice_shouldAssignAttributesCorrectlyToNewInstance_validInput() {
        var sensingDevice = new SensingDevice("serialNumber", "manufacturer", "model");
        assertNotNull(sensingDevice);
        assertDoesNotThrow(() -> UUID.fromString(sensingDevice.getId()));
        assertNotNull(sensingDevice.getSensors());
        assertEquals(sensingDevice.getSerialNumber(), "serialNumber");
        assertEquals(sensingDevice.getManufacturer(), "manufacturer");
        assertEquals(sensingDevice.getModel(), "model");
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void addSensor_shouldCreateAndAppendNewSensor_validInput() {
        validSensingDevice.addSensor("name", "description", "metadata");
        assertEquals(1, validSensingDevice.getSensors().size());
        assertEquals("name", validSensingDevice.getSensors().get(0).getName());
        assertEquals("description", validSensingDevice.getSensors().get(0).getDescription());
        assertEquals("metadata", validSensingDevice.getSensors().get(0).getMetadata());
        assertDoesNotThrow(() -> UUID.fromString(validSensingDevice.getSensors().get(0).getId()));
        assertNotNull(validSensingDevice.getSensors().get(0).getDatastreams());
        assertEquals(0, validSensingDevice.getSensors().get(0).getDatastreams().size());
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void addSensor_shouldThrowIllegalArgumentsException_invalidInput() {
        assertThrows(IllegalArgumentException.class, () -> validSensingDevice.addSensor("", "description", "metadata"));
        assertEquals(validSensingDevice.getSensors().size(), 0);
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void removeSensor_shouldRemoveGivenSensorFromDevice_validInput() {
        sensors.add(temperatureSensor);
        sensors.add(locationSensor);
        validSensingDevice.setSensors(sensors);
        validSensingDevice.removeSensor(temperatureSensor);
        assertEquals(1, validSensingDevice.getSensors().size());
        assertEquals(locationSensor, validSensingDevice.getSensors().get(0));
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void removeSensor_shouldThrowException_invalidInput() {
        var nonExistingSensor = new Sensor("nonExistingSensor", "description", "metadata");
        var emptySensor = new Sensor();
        assertThrows(IllegalArgumentException.class, () -> validSensingDevice.removeSensor(nonExistingSensor));
        assertThrows(IllegalArgumentException.class, () -> validSensingDevice.removeSensor(emptySensor));
        assertThrows(NullPointerException.class, () -> validSensingDevice.removeSensor(null));
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void observe_shouldInsertObservationToRightDatastream_validInput() {
        sensors.add(temperatureSensor);
        sensors.add(locationSensor);
        validSensingDevice.setSensors(sensors);
        var tempObservation = new Observation("temperature");
        var locObservation = new Observation("location");
        validSensingDevice.observe(locationSensor, location, locObservation);
        validSensingDevice.observe(temperatureSensor, temperature, tempObservation);
        assertTrue(
                temperatureSensor.getDatastreams().stream().anyMatch(d -> d.getObservedProperty().equals(temperature)));
        assertTrue(locationSensor.getDatastreams().stream().anyMatch(d -> d.getObservedProperty().equals(location)));
        assertTrue(temperatureSensor.getDatastreams().stream().filter(d -> d.getObservedProperty().equals(temperature))
                .findAny().get().getObservations().contains(tempObservation));
        assertFalse(temperatureSensor.getDatastreams().stream().filter(d -> d.getObservedProperty().equals(temperature))
                .findAny().get().getObservations().contains(locObservation));
        assertTrue(locationSensor.getDatastreams().stream().filter(d -> d.getObservedProperty().equals(location))
                .findAny().get().getObservations().contains(locObservation));
        assertFalse(locationSensor.getDatastreams().stream().filter(d -> d.getObservedProperty().equals(location))
                .findAny().get().getObservations().contains(tempObservation));
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void observe_shouldThrowException_invalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> validSensingDevice.observe(new Sensor(), temperature, new Observation("value")));
        assertThrows(NullPointerException.class,
                () -> validSensingDevice.observe(null, temperature, new Observation("value")));
        assertThrows(NullPointerException.class,
                () -> validSensingDevice.observe(temperatureSensor, null, new Observation("value")));
        assertThrows(NullPointerException.class,
                () -> validSensingDevice.observe(temperatureSensor, temperature, null));
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void getObservationsWithSensor_shouldCollectRightDatastreams_validInput() {
        var datastreams = new ArrayList<Datastream>();
        datastreams.add(locationDS);
        datastreams.add(temperatureDS);
        aggregateSensor.setDatastreams(datastreams);
        sensors.add(aggregateSensor);
        validSensingDevice.setSensors(sensors);
        assertTrue(validSensingDevice.getObservations(aggregateSensor).contains(locationDS));
        assertTrue(validSensingDevice.getObservations(aggregateSensor).contains(temperatureDS));
        assertEquals(2, validSensingDevice.getObservations(aggregateSensor).size());
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void getObservationsWithSensorAndObservedProperty_shouldCollectRightDatastream_validInput() {
        var datastreams = new ArrayList<Datastream>();
        datastreams.add(locationDS);
        datastreams.add(temperatureDS);
        aggregateSensor.setDatastreams(datastreams);
        sensors.add(aggregateSensor);
        validSensingDevice.setSensors(sensors);
        assertEquals(locationDS, validSensingDevice.getObservations(aggregateSensor, location));
        assertEquals(temperatureDS, validSensingDevice.getObservations(aggregateSensor, temperature));
        assertTrue(checkInvariants(validSensingDevice));
    }

    @Test
    public void getObservationsWithObservedProperty_shouldReturnNewMergedDatastream_validInput() {
        var locationDSObs = new TreeSet<Observation>(Collections.reverseOrder()) {
            {
                new Observation("1").setTimestamp(Instant.now().minusSeconds(1));
                new Observation("2").setTimestamp(Instant.now().minusSeconds(100));
            }
        };
        locationDS.setObservations(locationDSObs);
        var locationDS2Obs = new TreeSet<Observation>() {
            {
                new Observation("5").setTimestamp(Instant.now().minusSeconds(1000));
                new Observation("6").setTimestamp(Instant.now().minusSeconds(10000));
            }
        };
        locationDS2.setObservations(locationDS2Obs);

        var datastreams = new ArrayList<Datastream>();
        datastreams.add(locationDS);
        locationSensor.setDatastreams(datastreams);
        sensors.add(locationSensor);
        datastreams = new ArrayList<Datastream>();
        datastreams.add(locationDS2);
        locationSensor2.setDatastreams(datastreams);
        sensors.add(locationSensor2);
        validSensingDevice.setSensors(sensors);
        var mergedLocationDS = validSensingDevice.getObservations(location);
        assertEquals(location, mergedLocationDS.getObservedProperty());
        assertTrue(mergedLocationDS.getObservations().containsAll(locationDS.getObservations()));
        assertTrue(mergedLocationDS.getObservations().containsAll(locationDS2.getObservations()));
        assertFalse(mergedLocationDS.getObservations().stream().anyMatch(
                o -> !locationDS.getObservations().contains(o) || !locationDS2.getObservations().contains(o)));
        assertTrue(checkInvariants(validSensingDevice));
    }

    private boolean checkInvariants(SensingDevice device) {
        return device.getSensors().stream().distinct().toList().size() == device.getSensors().size();
    }

}
