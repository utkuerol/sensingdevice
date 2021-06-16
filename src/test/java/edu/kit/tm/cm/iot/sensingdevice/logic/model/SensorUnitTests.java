package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SensorUnitTests {

    private static Sensor locationSensor;
    private static Sensor aggregateSensor;
    private static ObservedProperty location = new ObservedProperty("location", "description", "unitOfMeasurement");
    private static ObservedProperty temperature = new ObservedProperty("temperature", "description",
            "unitOfMeasurement");

    @BeforeEach
    void setUp() {
        locationSensor = new Sensor("locationSensor", "description", "metadata");
        aggregateSensor = new Sensor("aggregateSensor", "description", "metadata");
    }

    @Test
    public void sensor_shouldAssignAttributesCorrectlyToNewInstance_validInput() {
        var sensor = new Sensor("name", "description", "metadata");
        assertNotNull(sensor);
        assertEquals("name", sensor.getName());
        assertEquals("description", sensor.getDescription());
        assertEquals("metadata", sensor.getMetadata());
        assertNotNull(sensor.getDatastreams());
        assertEquals(0, sensor.getDatastreams().size());
    }

    @Test
    public void observe_shouldRespectInvariantAndInsertNewObservation_validInput() {
        // datastream with the observed property doesnt exist, should create it and
        // insert observation into it
        var obs1 = new Observation("value");
        aggregateSensor.observe(location, obs1);
        assertEquals(1, aggregateSensor.getDatastreams().size());
        assertEquals(location, aggregateSensor.getDatastreams().get(0).getObservedProperty());
        assertEquals(1, aggregateSensor.getDatastreams().get(0).getObservations().size());
        assertTrue(aggregateSensor.getDatastreams().get(0).getObservations().contains(obs1));

        // datastream with the observed property already exists, shouldn't create new
        // datastream and insert into the existing one
        var obs2 = new Observation("value");
        aggregateSensor.observe(location, obs2);
        assertEquals(1, aggregateSensor.getDatastreams().size());
        assertEquals(location, aggregateSensor.getDatastreams().get(0).getObservedProperty());
        assertEquals(2, aggregateSensor.getDatastreams().get(0).getObservations().size());
        assertTrue(aggregateSensor.getDatastreams().get(0).getObservations().contains(obs1));
        assertTrue(aggregateSensor.getDatastreams().get(0).getObservations().contains(obs2));

        // datastream with the observed property doesnt exist, should create it and
        // insert observation into it.
        // should have 2 datastreams and 3 observations in total at the end
        var obs3 = new Observation("value");
        aggregateSensor.observe(temperature, obs3);
        assertEquals(2, aggregateSensor.getDatastreams().size());
        assertEquals(location, aggregateSensor.getDatastreams().get(0).getObservedProperty());
        assertEquals(temperature, aggregateSensor.getDatastreams().get(1).getObservedProperty());
        assertEquals(2, aggregateSensor.getDatastreams().get(0).getObservations().size());
        assertEquals(1, aggregateSensor.getDatastreams().get(1).getObservations().size());
        assertTrue(aggregateSensor.getDatastreams().get(1).getObservations().contains(obs3));
        assertTrue(checkInvariants(locationSensor));
    }

    @Test
    public void observe_shouldThrowException_invalidInput() {
        assertThrows(NullPointerException.class, () -> locationSensor.observe(null, new Observation("value")));
        assertThrows(NullPointerException.class, () -> locationSensor.observe(location, null));
        assertTrue(checkInvariants(locationSensor));
    }

    private boolean checkInvariants(Sensor sensor) {
        var numDifferentObservedProperties = sensor.getDatastreams().stream().map(d -> d.getObservedProperty())
                .distinct().toList().size();
        var numDatastreams = sensor.getDatastreams().size();
        return numDifferentObservedProperties == numDatastreams;
    }
}
