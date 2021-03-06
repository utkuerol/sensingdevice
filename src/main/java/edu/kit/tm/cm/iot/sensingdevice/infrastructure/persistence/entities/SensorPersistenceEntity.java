package edu.kit.tm.cm.iot.sensingdevice.infrastructure.persistence.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Table(name = "sensors")
public class SensorPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String uuid;

    private String name;

    private String description;

    private String metadata;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DatastreamPersistenceEntity> datastreams = new LinkedList<>();

}
