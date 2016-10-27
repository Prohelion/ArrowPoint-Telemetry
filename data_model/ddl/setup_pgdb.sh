#!/bin/bash

# Setup arrow1 database and create schema
# Run this as the postgres user
# Assumes required DDL scripts are in the same directory as this script


# Create database and setup required plsql language support
dropdb --username=postgres arrow1
createdb --username=postgres arrow1
createlang --username=postgres -d arrow1 plpgsql

# Create database schemas
psql --username=postgres -d arrow1 -a -f postgres.sql

# Create database functions
psql --username=postgres -d arrow1 -a -f functions.sql

# Load test data
psql --username=postgres -d arrow1 -a -f referencedata.sql
