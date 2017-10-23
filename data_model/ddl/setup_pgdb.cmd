@echo off

Rem Setup TeamArrow database and create schema / user
Rem Run this as the postgres user
Rem Assumes required DDL scripts are in the same directory as this script

SET PGPASSWORD=postgres

echo -----------------------------------------------------------------
echo SETUP THE DATABASE
echo -----------------------------------------------------------------
echo.

Rem Create database and setup required plsql language support
dropdb --username=postgres TeamArrow
pause
dropuser --username=postgres teamarrow
pause

echo -----------------------------------------------------------------
echo When prompted for the password answer ***REMOVED***
echo -----------------------------------------------------------------
echo.

createuser --username=postgres --createdb --pwprompt teamarrow
pause

SET PGPASSWORD=***REMOVED***

createdb --username=teamarrow TeamArrow
pause
createlang --username=teamarrow -d TeamArrow plpgsql

Rem Create database schemas
psql --username=teamarrow -d TeamArrow -a -f postgres.sql

Rem Create database functions
psql --username=teamarrow -d TeamArrow -a -f functions.sql

Rem Load test data
psql --username=teamarrow -d TeamArrow -a -f referencedata.sql
