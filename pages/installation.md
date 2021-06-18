# Installation

You need a PostgreSQL instance running to start locally. To ease setting up the database a Makefile is provided in the repository: 

- Configure your environment variables for the database: 
  - ```DB_USERNAME```
  - ```DB_PASSWORD```

- Run ```make postgres``` at the project root 
  
- Export a ```DB_URL```:
  - ```export DB_URL=localhost:5432/```

