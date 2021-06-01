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


context Datastream::insert(observation: Observation)
pre: forAll(o:Observation) | observation.timestamp.before(o.timestamp)
post: (self.getObservaions() -> exists (o:Observation | o.equals(observaion)) 
    and self.getObservations()@pre 	.forAll(o:Observation | self.getObservations().includes(o)) 
    and self.getObservations().size() = self. getObservations()@pre.size() + 1
```
