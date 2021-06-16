package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ObservedProperty {
    private String name;
    private String description;
    private String unitOfMeasurement;

    public ObservedProperty(String name, String description, String unitOfMeasurement) {
        this.name = name;
        this.description = description;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObservedProperty) {
            var property = (ObservedProperty) obj;
            if (property.getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
}
