DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'prohelion') THEN

      CREATE USER prohelion WITH CREATEDB PASSWORD 'passw0rd!';
   END IF;
END
$body$;