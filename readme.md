docker run -itd \
  -e POSTGRES_USER=mjwdbadmin \
  -e POSTGRES_PASSWORD=vf1p3nPRuibL \
  -p 5432:5432 \
  -v postgres-data:/var/lib/postgresql/data \
  --name postgres-container postgres:17


docker run -itd \
  --name mjw_service_container \
  -p 8080:8080 \
  --network mjw_network \
  -e DB_HOST=postgres-container \
  mjw_service:1.0.0

kill/start database
docker stop postgres-container
docker start postgres-container




TODO- items
LOW Centralize validations and implement AOP type validations, bifurcated validators
