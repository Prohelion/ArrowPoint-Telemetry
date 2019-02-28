CREATE or REPLACE FUNCTION calcRollingPowerAvg (period integer, sourceTable text, targetTable text, currentDataPointCanId integer, voltageDataPointCanId integer) returns void as
$$
DECLARE
	avg RECORD;
        avgCurrent double precision;
        avgVoltage double precision;
        tstamp1 timestamp;
        timeThreshold timestamp; 
	avgPower double precision;
        numSecs integer DEFAULT 20;
BEGIN
        IF period > 0 THEN
                numSecs := period;
        END IF;
        timeThreshold = now() at time zone 'utc' - (numSecs || ' seconds')::INTERVAL;

        FOR avg IN execute 'SELECT max(TSTAMP) as tstamp, avg(FVAL) as avg FROM ' || sourceTable || ' where tstamp > ' || quote_literal(timeThreshold) || 'and data_pnt_can_id =' || currentDataPointCanId
        LOOP

                -- avg contains a record from source table
                RAISE INFO 'Average for can pnt id (%d) is (%d)',currentDataPointCanId, avg.avg;
		avgCurrent = avg.avg;
		tstamp1 = avg.tstamp;
        END LOOP;
        FOR avg IN execute 'SELECT max(TSTAMP) as tstamp, avg(FVAL) as avg FROM ' || sourceTable || ' where tstamp > ' || quote_literal(timeThreshold) || 'and data_pnt_can_id =' || voltageDataPointCanId
        LOOP

                -- avg contains a record from source table
                RAISE INFO 'Average for can pnt id (%d) is (%d)',voltageDataPointCanId, avg.avg;
		avgVoltage = avg.avg;
        END LOOP;

	avgPower = avgCurrent * avgVoltage;

	execute 'INSERT into ' || targetTable || '(TSTAMP, AVG_POWER, INTERVAL) values (' || coalesce(quote_literal(tstamp1), 'NULL') ||', '|| avgPower ||', '|| period ||')';
END
$$ language plpgsql;

CREATE or REPLACE FUNCTION archiveCANData (period integer, sourceTable text, targetTable text) returns void as
$$
DECLARE
        msrmnt RECORD;
        timeThreshold timestamp;
        numSecs integer DEFAULT 20;
BEGIN
        IF period > 0 THEN
                numSecs := period;
        END IF;
        timeThreshold = now() at time zone 'utc' - (numSecs || ' seconds')::INTERVAL;

        FOR msrmnt IN execute 'SELECT ' || sourceTable || '_id as ID, DATA_PNT_CAN_ID, TSTAMP, EXTD, RTR, DATA_LEN, IVAL, CVAL, FVAL, STATE FROM ' || sourceTable || ' where tstamp < ' || quote_literal(timeThreshold)
        LOOP

                -- msrmnt contains a record from source table
                -- RAISE INFO 'Archiving mesurement record to short term archive with ID (%d) and CAN Packet ID (%d)', msrmnt.id, msrmnt.data_pnt_can_id;
                execute 'INSERT into ' || targetTable || '(DATA_PNT_CAN_ID, TSTAMP, EXTD, RTR, DATA_LEN, IVAL, CVAL, FVAL, STATE) values (' || msrmnt.DATA_PNT_CAN_ID ||', '|| coalesce(quote_literal(msrmnt.TSTAMP), 'NULL') ||', '|| msrmnt.EXTD ||', '|| msrmnt.RTR ||', '|| msrmnt.DATA_LEN ||', '|| coalesce(quote_literal(msrmnt.IVAL), 'NULL') ||', '|| coalesce(quote_literal(msrmnt.CVAL), 'NULL') ||', '|| coalesce(quote_literal(msrmnt.FVAL), 'NULL')||', '|| coalesce(quote_literal(msrmnt.STATE)) ||')';
                execute 'DELETE FROM ' || sourceTable || ' where ' || sourceTable || '_id = ' || msrmnt.id;
        END LOOP;
END
$$ language plpgsql;

CREATE OR REPLACE FUNCTION hex_to_int(hexval varchar) RETURNS integer AS $$
DECLARE
    result  int;
BEGIN
    EXECUTE 'SELECT x''' || hexval || '''::int' INTO result;
    RETURN result;
END;
$$ LANGUAGE 'plpgsql' IMMUTABLE STRICT;

