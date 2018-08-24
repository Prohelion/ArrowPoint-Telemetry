# Use an official TimescaleDB runtime as a parent image
FROM timescale/timescaledb:latest-pg10

ENV POSTGRES_USER "teamarrow"
ENV POSTGRES_PASSWORD "***REMOVED***"

ADD * /docker-entrypoint-initdb.d/