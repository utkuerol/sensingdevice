package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensingdevices")
public class SensingDevicePersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String uuid;

    private String serialNumber;

    private String manufacturer;

    private String model;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SensorPersistenceEntity> sensors = new LinkedList<>();

}
