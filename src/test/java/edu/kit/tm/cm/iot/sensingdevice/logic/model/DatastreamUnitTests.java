package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatastreamUnitTests {

    private static ObservedProperty location = new ObservedProperty("location", "description", "unitOfMeasurement");
    private static Datastream locationDS;

    @BeforeEach
    void setUp() {
        locationDS = new Datastream(location);
    }

    @Test
    public void datastream_shouldAssignAttributesCorrectlyToNewInstance_validInput() {
        var ds = new Datastream(location);
        assertNotNull(ds);
        assertEquals(location, ds.getObservedProperty());
        assertNotNull(ds.getObservations());
        assertEquals(0, ds.getObservations().size());
        assertTrue(checkInvariants(ds));
    }

    @Test
    public void insert_shouldAddNewObservationWithoutOverwritingExisting_validInput() {
        // insert new observation
        var obs1 = new Observation("1");
        obs1.setTimestamp(Instant.now().minusSeconds(1000));
        locationDS.insert(obs1);
        assertEquals(1, locationDS.getObservations().size());
        assertTrue(locationDS.getObservations().contains(obs1));

        // insert new observation
        var obs2 = new Observation("2");
        obs2.setTimestamp(Instant.now().minusSeconds(100));
        locationDS.insert(obs2);
        assertEquals(2, locationDS.getObservations().size());
        assertTrue(locationDS.getObservations().contains(obs1));
        assertTrue(locationDS.getObservations().contains(obs2));
        assertEquals(obs1, locationDS.getObservations().first());
        assertEquals(obs2, locationDS.getObservations().last());

        // insert the same observation again
        // should have no duplicates (=> no multiple observations at the same timestamp)
        locationDS.insert(obs2);
        assertEquals(2, locationDS.getObservations().size());
        assertTrue(locationDS.getObservations().contains(obs1));
        assertTrue(locationDS.getObservations().contains(obs2));
        assertEquals(obs1, locationDS.getObservations().first());
        assertEquals(obs2, locationDS.getObservations().last());

        // insert new observation
        var obs3 = new Observation("3");
        obs3.setTimestamp(Instant.now());
        locationDS.insert(obs3);
        assertEquals(3, locationDS.getObservations().size());
        assertTrue(locationDS.getObservations().contains(obs1));
        assertTrue(locationDS.getObservations().contains(obs2));
        assertTrue(locationDS.getObservations().contains(obs3));
        assertEquals(obs1, locationDS.getObservations().first());
        assertEquals(obs3, locationDS.getObservations().last());

        assertTrue(checkInvariants(locationDS));
    }

    private boolean checkInvariants(Datastream datastream) {
        // being sorted is already guarenteed by the SortedSet data type, no need to
        // explicitly assert the order
        return datastream.getObservedProperty() != null && !datastream.getObservedProperty().getName().equals("");
    }
}
