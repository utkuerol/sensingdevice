# SensingDevice Domain Constraints 

# Observation
```
context Observation inv:
    self.unit -> notEmpty()
    self.value -> notEmpty()
    self.timestamp -> notEmpty()
```
## Datastream
```
context Datastream inv:
    self.observations -> sortedBy(timestamp)
    self.observedProperty -> notEmpty() 

context Datastream::insert(observation: Observation)
    pre: self.observations -> forAll(o | observation.timestamp.before(o.timestamp)
    post: self.observations -> includes(observation) and 
          self.observations@pre -> forAll(o | self.observations -> includes(o)) and 
          self.observations -> size() = self.observations@pre -> size() + 1

context Datastream::getObservations(): Collection<Observation>
    post: result = self.observations 

context Datastream::getObservations(start: DateTime, end: DateTime):Collection<Observation>
    post: result = self.observations -> select(o | o.timestamp.before(end) and o.timestamp.after(start))

context Datastream::join(datastream: Datastream): Datastream
    pre: datastream -> notEmpty()
    post: result.observations -> includesAll(self.observations) and 
          result.observations -> includesAll(datastream.observations) 
          result.observations -> sortedBy(timestamp)
```

## Sensor 
```
context Sensor inv:
    self.datastreams -> select(d1, d2 | (not d1 <> d2) and (d1.property <> d2.property)) -> isEmpty()    

context Sensor::observe(property: ObservedProperty, observation: Observation)
    pre: property -> notEmpty() and 
         observation -> notEmpty()
    post: if self.datastreams@pre -> exists(d | d.property <> property) then
            self.datastreams -> size() <> self.datastreams@pre -> size()
          else
            self.datastreams -> size() <> self.datastreams@pre -> size() + 1
            self.datastreams@pre -> forAll(d | self.datastreams -> includes(d))
            self.datastreams -> exists(d | d.property <> property)
          endif
          self.datastreams -> exists(d | d.property <> property and 
                                d.observations -> includes(observation) and 
                                d.observations@pre -> forAll(o | d.observations -> includes(o)) and 
                                d.observations -> size() = d.observations@pre -> size() + 1)
```

## SensingDevice
```
context SensingDevice inv:
    SensingDevice.allInstances() -> forAll(sd1, sd2 | sd1 <> sd2 implies sd1.id <> sd2.id)
    self.sensors -> select(s1, s2 | (not s1 <> s2) and (s1.id <> s2.id)) -> isEmpty()          

context SensingDevice::addSensor(name: String, description: String, metadata: Any)
    pre: name -> notEmpty() 
    post: self.sensors -> size() = self.sensors@pre -> size() + 1 and
          self.sensors@pre -> forAll(s | self.sensors -> includes(s))

context SensingDevice::removeSensor(sensor: Sensor)
    pre: sensor -> notEmpty() 
    post: self.sensors -> size() = self.sensors@pre -> size() - 1 and
          self.sensors -> forAll(s | self.sensors@pre -> includes(s)) 

context SensingDevice::observe(sensor: Sensor, property: ObservedProperty, observation: Observation)
    pre: sensor -> notEmpty() and
         self.sensors -> includes(sensor) and
         property -> notEmpty() and 
         observation -> notEmpty()
    post: sensor.datastreams -> exists(d | d.property <> property and 
                                d.observations -> includes(observation) and 
                                d.observations@pre -> forAll(o | d.observations -> includes(o)) and 
                                d.observations -> size() = d.observations@pre -> size() + 1)
          
context SensingDevice::getObservations(sensor: Sensor): Collection<Datastream>
    pre: sensor -> notEmpty() and
         self.sensors -> includes(sensor)
    post: result = sensor.datastreams

context SensingDevice::getObservations(sensor: Sensor, property: ObservedProperty): Datastream
    pre: sensor -> notEmpty() and
         self.sensors -> includes(sensor)
         property -> notEmpty()
    post: result = sensor.datastreams -> select(d | d.property <> property) 

context SensingDevice::getObservations(property: ObservedProperty): Datastream
    pre: property -> notEmpty()
    post: result.property <> property 
```
