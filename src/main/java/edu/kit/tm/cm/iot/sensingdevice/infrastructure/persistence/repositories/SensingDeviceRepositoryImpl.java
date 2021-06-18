package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.mappers.SensingDeviceMapper;
import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories.dao.SensingDeviceDAO;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;

public class SensingDeviceRepositoryImpl implements SensingDeviceRepository {

    @Autowired
    private SensingDeviceDAO sensingDeviceDao;

    @Override
    public Collection<SensingDevice> findAll() {
        return sensingDeviceDao.findAll().stream().map(SensingDeviceMapper::fromPersistenceEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SensingDevice> findById(String deviceId) {
        var device = sensingDeviceDao.findByUuid(deviceId).stream().findFirst().get();
        return Optional.of(SensingDeviceMapper.fromPersistenceEntity(device));
    }

    @Override
    public Optional<SensingDevice> findByIdWithoutObservations(String deviceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<SensingDevice> findByIdWithObservationsFromDatastreamOnly(String deviceId, Datastream datastream) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<SensingDevice> findByIdWithObservationsFromDatastreamsOnly(String deviceId,
            Collection<Datastream> datastreams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(SensingDevice sensingDevice) {
        var sensingDevicePersistenceEntity = SensingDeviceMapper.toPersistenceEntity(sensingDevice);
        sensingDeviceDao.save(sensingDevicePersistenceEntity);
    }

    @Override
    public void delete(SensingDevice sensingDevice) {
        var sensingDevicePersistenceEntity = sensingDeviceDao.findByUuid(sensingDevice.getId()).stream().findFirst()
                .get();
        sensingDeviceDao.delete(sensingDevicePersistenceEntity);
    }

    @Override
    public void update(SensingDevice sensingDevice) {
        var persistedState = sensingDeviceDao.findByUuid(sensingDevice.getId()).stream().findFirst().get();
        var updatedState = SensingDeviceMapper.toPersistenceEntity(sensingDevice);
        updatedState.setId(persistedState.getId());
        sensingDeviceDao.save(updatedState);
    }

    @Override
    public void updateWithoutObservations(SensingDevice sensingDevice) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateWithObservationsFromDatastreamOnly(SensingDevice sensingDevice, Datastream datastream) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateWithObservationsFromDatastreamsOnly(SensingDevice sensingDevice,
            Collection<Datastream> datastreams) {
        // TODO Auto-generated method stub

    }

}
