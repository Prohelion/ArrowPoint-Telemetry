@echo off

Rem Setup arrow1 database and create schema
Rem Run this as the postgres user
Rem Assumes required DDL scripts are in the same directory as this script


Rem Create database and setup required plsql language support
createdb --username=postgres arrow1
createlang --username=postgres -d arrow1 plpgsql

Rem Create database schemas
psql --username=postgres -d arrow1 -a -f postgres.sql

Rem Create database functions
psql --username=postgres -d arrow1 -a -f functions.sql

Rem Load test data
psql --username=postgres -d arrow1 -a -f referencedata.sql
