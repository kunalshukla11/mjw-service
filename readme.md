docker run -itd \
  -e POSTGRES_USER=mjwdbadmin \
  -e POSTGRES_PASSWORD=vf1p3nPRuibL \
  -p 5432:5432 \
  -v postgres-data:/var/lib/postgresql/data \
  --network mjw_network \
  --name postgres-container postgres


docker run -itd \
  --name mjw_service_container \
  -p 8080:8080 \
  mjw_sevice

kill/start database
docker stop postgres-container
docker start postgres-container




TODO- items
LOW Centralize validations and implement AOP type validations, bifurcated validators
