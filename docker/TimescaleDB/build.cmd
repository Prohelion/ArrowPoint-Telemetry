del init.sql
type ..\..\data_model\ddl\postgres.sql > init.sql
type ..\..\data_model\ddl\functions.sql >> init.sql
type ..\..\data_model\ddl\referencedata.sql >> init.sql

docker build --no-cache -t prohelion/timescaledb-with-data:0.4 .

rem del *.sql
