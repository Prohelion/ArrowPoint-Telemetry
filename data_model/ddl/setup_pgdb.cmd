@echo off

SET PGPASSWORD=admin

echo --------------------------------------------------------------------------
echo FULL RELOAD OF THE DATABASE
echo. 
echo If you are not trying to do a full load see the instructions in setup_pgdb
echo --------------------------------------------------------------------------
echo.

Rem Setup TeamArrow database and create schema
Rem Run this as the postgres user
Rem Assumes required DDL scripts are in the same directory as this script

SET PGPASSWORD=admin

Rem Create database and setup required plsql language support
dropdb --username=postgres --if-exists TeamArrow

Rem Set the user teamarrow
psql --username=postgres -a -f create_user.sql
if %errorlevel% neq 0 exit /b %errorlevel%

createdb --username=postgres --owner=teamarrow TeamArrow
if %errorlevel% neq 0 exit /b %errorlevel%

createlang --username=postgres -d TeamArrow plpgsql

SET PGPASSWORD=teamarrow

Rem Create database schemas
psql --username=teamarrow -d TeamArrow -a -f postgres.sql
if %errorlevel% neq 0 exit /b %errorlevel%

Rem Create database functions
psql --username=teamarrow -d TeamArrow -a -f functions.sql
if %errorlevel% neq 0 exit /b %errorlevel%

Rem Load test data
psql --username=teamarrow -d TeamArrow -a -f referencedata.sql
if %errorlevel% neq 0 exit /b %errorlevel%

SET PGPASSWORD=

echo.
echo --------------------------------------------------------------------------
echo FULL RELOAD COMPLETE
echo --------------------------------------------------------------------------
