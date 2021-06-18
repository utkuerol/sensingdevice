package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.ObservationPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;

public class ObservationMapper {

    public static ObservationPersistenceEntity toPersistenceEntity(Observation observation) {
        var persistenceEntity = new ObservationPersistenceEntity();
        persistenceEntity.setTimestamp(observation.getTimestamp());
        persistenceEntity.setValue(observation.getValue());
        return persistenceEntity;
    }

    public static Observation fromPersistenceEntity(ObservationPersistenceEntity persistenceEntity) {
        var observation = new Observation(persistenceEntity.getValue(), persistenceEntity.getTimestamp());
        return observation;
    }
}
