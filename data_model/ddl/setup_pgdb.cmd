@echo off

Rem Setup TeamArrow database and create schema / user
Rem Run this as the postgres user
Rem Assumes required DDL scripts are in the same directory as this script

SET PGPASSWORD=***REMOVED***

echo -----------------------------------------------------------------
echo SETUP THE DATABASE
echo -----------------------------------------------------------------
echo.

Rem Create database and setup required plsql language support
dropdb --username=teamarrow teamarrow
createdb --username=teamarrow teamarrow
createlang --username=teamarrow -d teamarrow plpgsql

Rem Create database schemas
psql --username=teamarrow -d teamarrow -a -f postgres.sql

Rem Create database functions
psql --username=teamarrow -d teamarrow -a -f functions.sql

Rem Load test data
psql --username=teamarrow -d teamarrow -a -f referencedata.sql
