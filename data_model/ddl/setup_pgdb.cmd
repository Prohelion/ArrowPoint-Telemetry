@echo off

Rem Setup Prohelion database and create schema / user
Rem Run this as the postgres user
Rem Assumes required DDL scripts are in the same directory as this script

SET PGPASSWORD=passw0rd!

echo -----------------------------------------------------------------
echo SETUP THE DATABASE
echo -----------------------------------------------------------------
echo.

Rem Create database and setup required plsql language support
dropdb --username=prohelion prohelion
createdb --username=prohelion prohelion
createlang --username=prohelion -d prohelion plpgsql

Rem Create database schemas
psql --username=prohelion -d prohelion -a -f postgres.sql

Rem Create database functions
psql --username=prohelion -d prohelion -a -f functions.sql

Rem Load test data
psql --username=prohelion -d prohelion -a -f referencedata.sql
