file=$1
database=$2

if [ $# -ne 2 ]; then
	echo "conv_to_psql <oracle_file> <psql database nane>"
	exit 0
fi

psql -d $database -a -c "SELECT 'ALTER TABLE '||nspname||'.'||relname||' DROP CONSTRAINT '||conname||';' FROM pg_constraint INNER JOIN pg_class ON conrelid=pg_class.oid INNER JOIN pg_namespace ON pg_namespace.oid=pg_class.relnamespace ORDER BY CASE WHEN contype='f' THEN 0 ELSE 1 END,contype,nspname,relname,conname" | grep "^ ALTER"

cat $file | awk '{ gsub(/NUMBER/,"INTEGER"); gsub(/FLOAT/, "DOUBLE PRECISION"); gsub(/CHAR \(1\)/, "BOOLEAN"); gsub(/DATE/, "TIMESTAMP"); gsub(/ RAW \(.*\)/, " BYTEA"); gsub(/CASCADE CONSTRAINTS/, "CASCADE"); gsub(/VARCHAR2/, "VARCHAR"); gsub(/_ID INTEGER *NOT NULL/, "_ID BIGSERIAL NOT NULL"); print }'
