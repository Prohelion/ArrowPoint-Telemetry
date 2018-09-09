# Use an official TimescaleDB runtime as a parent image
FROM timescale/timescaledb:latest-pg10

ENV POSTGRES_USER "teamarrow"
ENV POSTGRES_PASSWORD "***REMOVED***"

COPY *.sqld /docker-entrypoint-initdb.d/
COPY *.sh /docker-entrypoint-initdb.d/