# Installation

You need a PostgreSQL instance running to start locally. To ease setting up the database a Makefile is provided in the repository: 

- Configure your environment variables for the database: 
  - ```POSTGRESQL_DATABASE_USER```
  - ```POSTGRESQL_DATABASE_PASSWORD```

- Run ```$ make postgres``` at the project root 
  
- Export a ```POSTGRESQL_DATABASE_HOST```:
  - ```$ export POSTGRESQL_DATABASE_HOST=localhost:5432```

- Export a ```POSTGRESQL_DATABASE_NAME```

- Start the Spring Boot application 