postgres:
	docker run -d \
	-e POSTGRES_PASSWORD=${DB_PASSWORD} \
	-e POSTGRES_USER=${DB_USERNAME} \
	-p 5432:5432 \
	--name sensingdevice-postgres postgres 

test:
	. .env	&& ./mvnw test 