package edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories;

import java.util.Collection;
import java.util.Optional;

import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;

public interface SensingDeviceRepository {

    Collection<SensingDevice> findAll();

    // fetches all aggregations including observations from all datastreams
    Optional<SensingDevice> findById(String deviceId);

    // fetches all aggregations except the observations of datastreams
    Optional<SensingDevice> findByIdWithoutObservations(String deviceId);

    // same as findById but only observations from @param datastream are fetched
    Optional<SensingDevice> findByIdWithObservationsFromDatastreamOnly(String deviceId, Datastream datastream);

    // same as findById but only observations from @param datastreams are fetched
    Optional<SensingDevice> findByIdWithObservationsFromDatastreamsOnly(String deviceId,
            Collection<Datastream> datastreams);

    void create(SensingDevice sensingDevice);

    void delete(SensingDevice sensingDevice);

    // updates the whole entity
    void update(SensingDevice sensingDevice);

    // doesnt update any observations
    void updateWithoutObservations(SensingDevice sensingDevice);

    // doesnt update observations from datastreams except
    // @param datastreams
    void updateWithObservationsFromDatastreamOnly(SensingDevice sensingDevice, Datastream datastream);

    // doesnt update observations from datastreams not included in
    // @param datastreams
    void updateWithObservationsFromDatastreamsOnly(SensingDevice sensingDevice, Collection<Datastream> datastreams);

}
