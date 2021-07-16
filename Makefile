postgres:
	docker run -d \
	-e POSTGRES_PASSWORD=${POSTGRESQL_DATABASE_PASSWORD} \
	-e POSTGRES_USER=${POSTGRESQL_DATABASE_USER} \
	-p 5432:5432 \
	--name sensingdevice-postgres postgres 

test:
	. .env	&& ./mvnw test 