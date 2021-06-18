package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities.SensingDevicePersistenceEntity;

@Repository
public interface SensingDeviceDAO extends JpaRepository<SensingDevicePersistenceEntity, Long> {

    List<SensingDevicePersistenceEntity> findAll();

    List<SensingDevicePersistenceEntity> findByUuid(String uuid);

}
