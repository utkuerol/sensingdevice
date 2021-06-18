package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.ObservationPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;

public class ObservationMapperUnitTests {

    private static Observation observation;
    private static ObservationPersistenceEntity observationPE;

    @BeforeEach
    void setUp() {
        var timestamp = Instant.now();
        observation = new Observation("value", timestamp);
        observationPE = new ObservationPersistenceEntity();
        observationPE.setTimestamp(timestamp);
        observationPE.setValue("value");
    }

    @Test
    public void toPersistenceEntity() {
        assertEquals(observationPE, ObservationMapper.toPersistenceEntity(observation));
    }

    @Test
    public void fromPersistenceEntity() {
        assertEquals(observation, ObservationMapper.fromPersistenceEntity(observationPE));
    }

}
