package edu.kit.tm.cm.iot.sensingdevice.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sensor {

	private String id;

	private String name;

	private String description;

	private String metadata;

	private List<Datastream> datastreams;

	public Sensor(String name, String description, String metadata) {
		this.name = name;
		this.description = description;
		this.metadata = metadata;
		this.id = UUID.randomUUID().toString().toUpperCase();
		this.datastreams = new ArrayList<>();
	}

	public void observe(@NonNull ObservedProperty observedProperty, @NonNull Observation observation) {
		var existingDatastreams = this.datastreams.stream()
				.filter(d -> d.getObservedProperty().equals(observedProperty)).collect(Collectors.toList());
		if (existingDatastreams.size() > 1) {
			throw new IllegalStateException(
					"This sensor should not have had more than one datastream with the same observed property");
		} else if (existingDatastreams.size() == 1) {
			var d = existingDatastreams.get(0);
			d.insert(observation);
		} else {
			var d = new Datastream(observedProperty);
			d.insert(observation);
			this.datastreams.add(d);
		}
	}

	@Override
	public boolean equals(@NonNull Object obj) {
		if (obj instanceof Sensor) {
			var sensor = (Sensor) obj;
			if (sensor.getId().equals(this.getId())) {
				return true;
			}
		}
		return false;
	}

}
