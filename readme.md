docker run -itd -e POSTGRES_USER=mjwdbadmin -e POSTGRES_PASSWORD=vf1p3nPRuibL -p 5432:5432 -v postgres-data:
/var/lib/postgresql/data --name postgres-container postgres
kill database
docker stop postgres-container
docker start postgres-container

TODO- items
LOW Centralize validations and implement AOP type validations, bifurcated validators