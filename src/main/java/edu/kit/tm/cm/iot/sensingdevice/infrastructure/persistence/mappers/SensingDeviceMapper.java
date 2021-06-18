package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers;

import java.util.stream.Collectors;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensingDevicePersistenceEntity;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;

public class SensingDeviceMapper {

    public static SensingDevicePersistenceEntity toPersistenceEntity(SensingDevice sensingDevice) {
        var persistenceEntity = new SensingDevicePersistenceEntity();
        persistenceEntity.setUuid(sensingDevice.getId());
        persistenceEntity.setManufacturer(sensingDevice.getManufacturer());
        persistenceEntity.setModel(sensingDevice.getModel());
        persistenceEntity.setSerialNumber(sensingDevice.getSerialNumber());
        persistenceEntity.setSensors(sensingDevice.getSensors().stream().map(SensorMapper::toPersistenceEntity)
                .collect(Collectors.toList()));
        return persistenceEntity;
    }

    public static SensingDevice fromPersistenceEntity(SensingDevicePersistenceEntity persistenceEntity) {
        var sensingDevice = new SensingDevice(persistenceEntity.getUuid(), persistenceEntity.getSerialNumber(),
                persistenceEntity.getManufacturer(), persistenceEntity.getManufacturer(), persistenceEntity.getSensors()
                        .stream().map(SensorMapper::fromPersistenceEntity).collect(Collectors.toList()));
        return sensingDevice;
    }

}
