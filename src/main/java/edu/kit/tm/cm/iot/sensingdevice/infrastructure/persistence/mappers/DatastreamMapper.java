package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import java.util.TreeSet;
import java.util.stream.Collectors;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.DatastreamPersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;

public class DatastreamMapper {

    public static DatastreamPersistenceEntity toPersistenceEntity(Datastream datastream) {
        var persistenceEntity = new DatastreamPersistenceEntity();
        persistenceEntity.setObservations(datastream.getObservations().stream()
                .map(ObservationMapper::toPersistenceEntity).collect(Collectors.toList()));
        persistenceEntity.setObservedPropertyDescription(datastream.getObservedProperty().getDescription());
        persistenceEntity.setObservedPropertyName(datastream.getObservedProperty().getName());
        persistenceEntity.setObservedPropertyUnitOfMeasurement(datastream.getObservedProperty().getUnitOfMeasurement());
        return persistenceEntity;
    }

    public static Datastream fromPersistenceEntity(DatastreamPersistenceEntity persistenceEntity) {
        var obs = new TreeSet<Observation>(persistenceEntity.getObservations().stream()
                .map(ObservationMapper::fromPersistenceEntity).collect(Collectors.toList()));
        var datastream = new Datastream(obs,
                new ObservedProperty(persistenceEntity.getObservedPropertyName(),
                        persistenceEntity.getObservedPropertyDescription(),
                        persistenceEntity.getObservedPropertyUnitOfMeasurement()));
        return datastream;
    }

}
