DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'teamarrow') THEN

      CREATE USER teamarrow WITH CREATEDB PASSWORD 'teamarrow';
   END IF;
END
$body$;