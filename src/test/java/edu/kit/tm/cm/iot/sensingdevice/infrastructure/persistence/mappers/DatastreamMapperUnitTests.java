package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.DatastreamPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.ObservationPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;

public class DatastreamMapperUnitTests {

    private static Datastream datastream;
    private static DatastreamPersistenceEntity datastreamPE;
    private ObservedProperty property = new ObservedProperty("name", "description", "unitOfMeasurement");

    @BeforeEach
    void setUp() {
        var timestamp = Instant.now();
        var timestamp2 = Instant.now().minusSeconds(100);

        var obs = Stream.of(new Observation("value", timestamp), new Observation("value", timestamp2))
                .collect(Collectors.toList());
        datastream = new Datastream(new TreeSet<Observation>(obs), property);

        var obsPE = Stream.of(new ObservationPersistenceEntity(0, "value", timestamp),
                new ObservationPersistenceEntity(0, "value", timestamp2)).collect(Collectors.toList());
        datastreamPE = new DatastreamPersistenceEntity(0, obsPE, property.getName(), property.getDescription(),
                property.getUnitOfMeasurement());
    }

    @Test
    public void toPersistenceEntity() {
        var pe = DatastreamMapper.toPersistenceEntity(datastream);
        assertEquals(pe.getId(), datastreamPE.getId());
        assertEquals(pe.getObservedPropertyDescription(), datastreamPE.getObservedPropertyDescription());
        assertEquals(pe.getObservedPropertyName(), datastreamPE.getObservedPropertyName());
        assertEquals(pe.getObservedPropertyUnitOfMeasurement(), datastreamPE.getObservedPropertyUnitOfMeasurement());
        assertTrue(datastreamPE.getObservations().containsAll(pe.getObservations()));
        assertTrue(pe.getObservations().containsAll(datastreamPE.getObservations()));
    }

    @Test
    public void fromPersistenceEntity() {
        assertEquals(datastream, DatastreamMapper.fromPersistenceEntity(datastreamPE));
    }

}
