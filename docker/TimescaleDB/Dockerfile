# Use an official TimescaleDB runtime as a parent image
FROM timescale/timescaledb:latest-pg10

ENV POSTGRES_USER "prohelion"
ENV POSTGRES_PASSWORD "passw0rd!"
ENV POSTGRES_DB prohelion

WORKDIR /docker-entrypoint-initdb.d
COPY *.sql /docker-entrypoint-initdb.d/  