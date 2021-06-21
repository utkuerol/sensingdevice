package edu.kit.tm.cm.iot.sensingdevice.logic.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.repositories.SensingDeviceRepositoryImpl;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Datastream;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Observation;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.ObservedProperty;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.SensingDevice;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.Sensor;
import edu.kit.tm.cm.iot.sensingdevice.logic.model.repositories.SensingDeviceRepository;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensingDeviceNotFoundException;
import edu.kit.tm.cm.iot.sensingdevice.logic.operations.exceptions.SensorNotFoundException;

public class ObservationOperationsUnitTests {

        private static SensingDeviceRepository mockRepository = mock(SensingDeviceRepositoryImpl.class);
        private static ObservationOperations observationOperations = new ObservationOperations(mockRepository);
        private static SensingDevice sensingDevice;
        private static Sensor sensor;
        private static ObservedProperty location = new ObservedProperty("location", "description", "unitOfMeasurement");
        private static ObservedProperty temperature = new ObservedProperty("temperature", "description",
                        "unitOfMeasurement");

        @BeforeEach
        void setUp() {
                sensor = new Sensor("name", "description", "metadata");
                sensor.getDatastreams().add(new Datastream(location));
                sensor.getDatastreams().add(new Datastream(temperature));
                sensor.observe(location, new Observation("1", Instant.MIN.plusSeconds(50)));
                sensor.observe(location, new Observation("2", Instant.MIN.plusSeconds(100)));
                sensor.observe(temperature, new Observation("3", Instant.MIN.plusSeconds(50)));
                sensor.observe(temperature, new Observation("4", Instant.MIN.plusSeconds(100)));
                sensingDevice = new SensingDevice("serialNumber", "manufacturer", "model");
                sensingDevice.getSensors().add(sensor);
                when(mockRepository.findById(sensingDevice.getId())).thenReturn(Optional.of(sensingDevice));
        }

        @Test
        public void getObservationsOfSensor_withoutTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                assertEquals(2, observationOperations.getObservationsOfSensor(sensingDevice.getId(), sensor.getId(),
                                Optional.empty(), Optional.empty()).size());
        }

        @Test
        public void getObservationsOfSensor_withTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var result = observationOperations.getObservationsOfSensor(sensingDevice.getId(), sensor.getId(),
                                Optional.of(Instant.MIN.plusSeconds(90)), Optional.of(Instant.MIN.plusSeconds(110)));
                assertEquals("2", result.get(0).getObservations().first().getValue());
                assertEquals("2", result.get(0).getObservations().last().getValue());
                assertEquals("4", result.get(1).getObservations().first().getValue());
                assertEquals("4", result.get(1).getObservations().last().getValue());

                result = observationOperations.getObservationsOfSensor(sensingDevice.getId(), sensor.getId(),
                                Optional.of(Instant.MIN.plusSeconds(49)), Optional.of(Instant.MIN.plusSeconds(51)));
                assertEquals("1", result.get(0).getObservations().first().getValue());
                assertEquals("1", result.get(0).getObservations().last().getValue());
                assertEquals("3", result.get(1).getObservations().first().getValue());
                assertEquals("3", result.get(1).getObservations().last().getValue());
        }

        @Test
        public void getObservationsOfSensorWithObservedProperty_withoutTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var result = observationOperations.getObservationsOfSensorWithObservedProperty(sensingDevice.getId(),
                                sensor.getId(), location.getName(), Optional.empty(), Optional.empty());
                assertEquals("1", result.getObservations().first().getValue());
                assertEquals("2", result.getObservations().last().getValue());

                result = observationOperations.getObservationsOfSensorWithObservedProperty(sensingDevice.getId(),
                                sensor.getId(), temperature.getName(), Optional.empty(), Optional.empty());
                assertEquals("3", result.getObservations().first().getValue());
                assertEquals("4", result.getObservations().last().getValue());
        }

        @Test
        public void getObservationsOfSensorWithObservedProperty_withTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var result = observationOperations.getObservationsOfSensorWithObservedProperty(sensingDevice.getId(),
                                sensor.getId(), location.getName(), Optional.of(Instant.MIN.plusSeconds(90)),
                                Optional.of(Instant.MIN.plusSeconds(110)));
                assertEquals("2", result.getObservations().first().getValue());
                assertEquals("2", result.getObservations().last().getValue());

                result = observationOperations.getObservationsOfSensorWithObservedProperty(sensingDevice.getId(),
                                sensor.getId(), temperature.getName(), Optional.of(Instant.MIN.plusSeconds(90)),
                                Optional.of(Instant.MIN.plusSeconds(110)));
                assertEquals("4", result.getObservations().first().getValue());
                assertEquals("4", result.getObservations().last().getValue());
        }

        @Test
        public void getObservationsWithObservedProperty_withoutTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensor2 = new Sensor("name2", "description", "metadata");
                sensor2.getDatastreams().add(new Datastream(location));
                sensor2.observe(location, new Observation("5", Instant.MIN.plusSeconds(70))); // after 1
                sensor2.observe(location, new Observation("6", Instant.MIN.plusSeconds(110))); // after 2
                sensingDevice.getSensors().add(sensor2);

                var result = observationOperations.getObservationsWithObservedProperty(sensingDevice.getId(),
                                location.getName(), Optional.empty(), Optional.empty());
                assertEquals("1", result.getObservations().first().getValue());
                assertEquals("6", result.getObservations().last().getValue());
                assertEquals(4, result.getObservations().size());
                assertTrue(result.getObservations().stream().anyMatch(o -> o.getValue().equals("5")));
                assertTrue(result.getObservations().stream().anyMatch(o -> o.getValue().equals("6")));
        }

        @Test
        public void getObservationsWithObservedProperty_withTimeFilter()
                        throws SensingDeviceNotFoundException, SensorNotFoundException {
                var sensor2 = new Sensor("name2", "description", "metadata");
                sensor2.getDatastreams().add(new Datastream(location));
                sensor2.observe(location, new Observation("5", Instant.MIN.plusSeconds(70))); // after 1
                sensor2.observe(location, new Observation("6", Instant.MIN.plusSeconds(110))); // after 2
                sensingDevice.getSensors().add(sensor2);

                var result = observationOperations.getObservationsWithObservedProperty(sensingDevice.getId(),
                                location.getName(), Optional.of(Instant.MIN.plusSeconds(51)),
                                Optional.of(Instant.MIN.plusSeconds(101)));
                assertEquals("5", result.getObservations().first().getValue());
                assertEquals("2", result.getObservations().last().getValue());
                assertEquals(2, result.getObservations().size());
        }
}
