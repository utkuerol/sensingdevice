package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.DatastreamPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.ObservationPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensorPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;

public class SensorMapperUnitTests {

    private static Sensor sensor;
    private static SensorPersistenceEntity sensorPE;
    private ObservedProperty property = new ObservedProperty("name", "description", "unitOfMeasurement");
    private ObservedProperty property2 = new ObservedProperty("name2", "description", "unitOfMeasurement");

    @BeforeEach
    void setUp() {
        var datastreams = Stream.of(new Datastream(property), new Datastream(property2)).collect(Collectors.toList());
        var datastreamsPE = Stream.of(
                new DatastreamPersistenceEntity(0, new LinkedList<ObservationPersistenceEntity>(), "name",
                        "description", "unitOfMeasurement"),
                new DatastreamPersistenceEntity(0, new LinkedList<ObservationPersistenceEntity>(), "name2",
                        "description", "unitOfMeasurement"))
                .collect(Collectors.toList());
        sensor = new Sensor("id", "name", "description", "metadata", datastreams);
        sensorPE = new SensorPersistenceEntity(0, "id", "name", "description", "metadata", datastreamsPE);
    }

    @Test
    public void toPersistenceEntity() {
        assertEquals(sensorPE, SensorMapper.toPersistenceEntity(sensor));
    }

    @Test
    public void fromPersistenceEntity() {
        assertEquals(sensor, SensorMapper.fromPersistenceEntity(sensorPE));
    }

}
