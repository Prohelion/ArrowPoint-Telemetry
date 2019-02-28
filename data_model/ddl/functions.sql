CREATE OR REPLACE FUNCTION hex_to_int(hexval varchar) RETURNS integer AS $$
DECLARE
    result  int;
BEGIN
    EXECUTE 'SELECT x''' || hexval || '''::int' INTO result;
    RETURN result;
END;
$$ LANGUAGE 'plpgsql' IMMUTABLE STRICT;

