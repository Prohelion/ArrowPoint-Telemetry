# Setup arrow1 database and create schema
# Run this as the postgres user
# Assumes required DDL scripts are in the same directory as this script
export PGPASSWORD=***REMOVED***
echo -----------------------------------------------------------------
echo SETUP THE DATABASE
echo -----------------------------------------------------------------
# Create database and setup required plsql language support
createlang --username=teamarrow -d teamarrow plpgsql
# Create database schemas
psql --username=teamarrow -d teamarrow -a -f /docker-entrypoint-initdb.d/postgres.sqld
# Create database functions
psql --username=teamarrow -d teamarrow -a -f /docker-entrypoint-initdb.d/functions.sqld
# Load test data
psql --username=teamarrow -d teamarrow -a -f /docker-entrypoint-initdb.d/referencedata.sqld
