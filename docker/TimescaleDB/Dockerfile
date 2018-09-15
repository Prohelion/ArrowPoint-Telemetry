# Use an official TimescaleDB runtime as a parent image
FROM timescale/timescaledb:latest-pg10

ENV POSTGRES_USER "teamarrow"
ENV POSTGRES_PASSWORD "***REMOVED***"
ENV POSTGRES_DB teamarrow

WORKDIR /docker-entrypoint-initdb.d
COPY *.sql /docker-entrypoint-initdb.d/  