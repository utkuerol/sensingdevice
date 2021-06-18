package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensorPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;

public class SensorMapper {

    public static SensorPersistenceEntity toPersistenceEntity(Sensor sensor) {
        var persistenceEntity = new SensorPersistenceEntity();
        persistenceEntity.setDescription(sensor.getDescription());
        persistenceEntity.setName(sensor.getName());
        persistenceEntity.setUuid(sensor.getId());
        persistenceEntity.setMetadata(sensor.getMetadata());
        persistenceEntity
                .setDatastreams(sensor.getDatastreams().stream().map(DatastreamMapper::toPersistenceEntity).toList());
        return persistenceEntity;
    }

    public static Sensor fromPersistenceEntity(SensorPersistenceEntity persistenceEntity) {
        var ds = persistenceEntity.getDatastreams().stream().map(DatastreamMapper::fromPersistenceEntity).toList();
        var sensor = new Sensor(persistenceEntity.getUuid(), persistenceEntity.getName(),
                persistenceEntity.getDescription(), persistenceEntity.getDescription(), ds);
        return sensor;
    }
}
