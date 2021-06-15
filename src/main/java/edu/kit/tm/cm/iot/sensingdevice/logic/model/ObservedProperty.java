package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ObservedProperty {
    private String name;
    private String description;
    private String unitOfMeasurement;

    public ObservedProperty(String name, String description, String unitOfMeasurement) {
        this.name = name;
        this.description = description;
        this.unitOfMeasurement = unitOfMeasurement;
    }
}
