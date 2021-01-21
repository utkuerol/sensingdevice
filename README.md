# Template for Microservice Documentation
As an example for documentation, the following microservice documentations can be used:
- [Vehicle MS](https://git.scc.kit.edu/cm-tm/cm-team/2-2.connectedcar/microservices/msvehicle)
- [Diagnosis MS](https://git.scc.kit.edu/cm-tm/cm-team/2-2.connectedcar/microservices/msdiagnosis)

*Note: The documentation template starts here:*

# *Name* Microservice

**Note: The documentation artifacts are stored in the folder "pages/"**

[Introduction](./pages/introduction.md) /
[API](./pages/api.md) /


*Short description of the  microservice and its responsibility*

# Table of Contents
* [Installation](#Installation)
    * [Configuration](#Configuration)
    * [Database](#Database)
* [Development](#development)
    * [Used Technologies](#used-technologies)
    * [Repository Structure](#repository-structure)
    * [Tests](#tests)
    * [DevOps](#DevOps)
* [Additional Notes](#additional-notes)
    * [People in charge](#people-in-charge)
    * [Attribution](#attribution)

# Installation

Description of the required installation steps.*

## Configuration
For configuration the microservice requires the following environment variables:

| Variable       | Description            | Default |
| -------------- | ---------------------- | ------- |
| MS_SERVER_PORT | the application's port | 8080    |
| ...            |                        |         |

## Database

*If a database is used, an explanation of the required database and setup is given.*


# Development

## Used Technologies

*- Technologies and references*

## Repository Structure
```

src
├── main
│   ├── java
│   │   └── edu/kit/tm/cm/...
    ...
```

## Tests
The tests in the current directory can be run with
```
run commands ...
```
### Code Coverage

...

## DevOps

*Documentation of the used DevOps templates and version*



# Additional Notes

*Domain Microservice Template Version 1.0.0*

### People in charge
- Long-term responsible: 
- Involved SeniorStudents: 

### Attribution

The repository's icon was taken from [flaticon](https://www.flaticon.com/free-icon/health-report_2928179).
