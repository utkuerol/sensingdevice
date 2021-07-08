# DevOps Template Configuration

See [CM Maven DevOps Template](https://git.scc.kit.edu/cm-tm/cm-team/1-1.cmdocumentation/1.templates/2.devops/maven)

## Helm values.yaml Variable Substitution

Custom variables, regarding the deployment, can be set in the helm/.env file. These variables are used to substitute variables defined in the helm/values.yaml. You can define any variable like this in the helm/.env file:

```
export INGRESS_HOSTNAME=cm-$RELEASE_SLUG.cloud.iai.kit.edu
```

The variables that should be substituted should be defined like this in the helm/values.yaml

```
- host: $INGRESS_HOSTNAME
```

The variables $INGRESS_HOSTNAME and $IMAGE_FULL_NAME shouldn't be changed unless advised by DevOps, e.g. in order to adapt to pipeline changes.

## Database Configuration

The Postgres Database is currently enabled in the `helm/values.yaml`:

```
postgresql:
    enabled: true
```

The username, password and database name can be configured through the following values:
```
postgresqlUsername: postgres
postgresqlPassword: postgres
postgresqlDatabase: postgresDB
```
Though, changing this shouldn't be necessary in a development environment.

Within the microservice pod, the following environment variables provide the necessary connection information for the database:
```
POSTGRESQL_DATABASE_USER
POSTGRESQL_DATABASE_PASSWORD
POSTGRESQL_DATABASE_NAME
POSTGRESQL_DATABASE_HOST
```
The application should configure the database connection using those environment variables.
