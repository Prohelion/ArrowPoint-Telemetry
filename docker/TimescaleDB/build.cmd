copy ..\..\data_model\ddl\postgres.sql postgres.sqld
copy ..\..\data_model\ddl\functions.sql functions.sqld
copy ..\..\data_model\ddl\referencedata.sql referencedata.sqld
copy ..\..\data_model\ddl\setup_pgdb_docker.sh .

docker build -t prohelion/timescaledb-with-data:0.3 .

del *.sql
del *.sqld
del *.sh