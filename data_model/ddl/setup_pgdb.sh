#!/bin/bash

# Setup arrow1 database and create schema
# Run this as the postgres user
# Assumes required DDL scripts are in the same directory as this script

export PGPASSWORD=passw0rd!

echo -----------------------------------------------------------------
echo SETUP THE DATABASE
echo -----------------------------------------------------------------

# Create database and setup required plsql language support
dropdb --username=prohelion prohelion
createdb --username=prohelion prohelion
createlang --username=prohelion -d prohelion plpgsql

# Create database schemas
psql --username=prohelion -d prohelion -a -f postgres.sql

# Create database functions
psql --username=prohelion -d prohelion -a -f functions.sql

# Load test data
psql --username=prohelion -d prohelion -a -f referencedata.sql
