CREATE EXTENSION IF NOT EXISTS tablefunc;

CREATE TABLE DATA_PNT 
    ( 
     DATA_PNT_ID BIGINT NOT NULL , 
     DATA_PNT_CAN_ID INTEGER , 
     NAME VARCHAR (50) , 
     DESCR VARCHAR (256) , 
     DATA_LEN INTEGER , 
     CAN_DATA_OFFST INTEGER , 
     DATA_TYPE VARCHAR , 
     LOW_ERR_THRHLD DOUBLE PRECISION , 
     LOW_WRNG_THRHLD DOUBLE PRECISION , 
     HIGH_WRNG_THRHLD DOUBLE PRECISION , 
     HIGH_ERR_THRHLD DOUBLE PRECISION , 
     MSRMNT_ID_FK BIGINT NOT NULL , 
     UNITS VARCHAR (30) 
    ) 
;


CREATE INDEX DATA_PNT__IDXv1 ON DATA_PNT 
    ( 
     DATA_PNT_CAN_ID ASC 
    ) 
;

ALTER TABLE DATA_PNT 
    ADD CONSTRAINT DATA_PNT_PK PRIMARY KEY ( DATA_PNT_ID ) ;



CREATE TABLE DEV 
    ( 
     DEV_ID BIGINT NOT NULL , 
     DEV_NAME VARCHAR (255) , 
     DEV_ABBREV VARCHAR (10) 
    ) 
;



ALTER TABLE DEV 
    ADD CONSTRAINT DEV_PK PRIMARY KEY ( DEV_ID ) ;



CREATE TABLE DEV_TYPE 
    ( 
     DEV_TYPE_ID BIGINT NOT NULL , 
     DEV_TYPE VARCHAR (20) 
    ) 
;



ALTER TABLE DEV_TYPE 
    ADD CONSTRAINT DEV_TYPE_PK PRIMARY KEY ( DEV_TYPE_ID ) ;


ALTER TABLE DEV_TYPE 
    ADD CONSTRAINT DEV_TYPE_UK UNIQUE ( DEV_TYPE ) ;



CREATE TABLE LNG_TERM_TREND_DATA 
    ( 
     LNG_TERM_TREND_DATA_ID BIGSERIAL NOT NULL , 
     DATA_PNT_CAN_ID INTEGER , 
     TSTAMP TIMESTAMPTZ , 
     EXTD BOOLEAN , 
     RTR BOOLEAN , 
     DATA_LEN INTEGER , 
     FVAL DOUBLE PRECISION , 
     IVAL INTEGER , 
     CVAL VARCHAR (16) , 
     STATE VARCHAR (10) CHECK ( STATE IN ('HighErr', 'HighWarn', 'LowErr', 'LowWarn', 'Normal')) 
    ) 
;

CREATE TABLE AVG_POWER
    (
     AVG_POWER_ID BIGSERIAL NOT NULL ,
     TSTAMP TIMESTAMPTZ ,
     AVG_POWER DOUBLE PRECISION ,
     INTERVAL INTEGER
    )
;

ALTER TABLE AVG_POWER 
    ADD CONSTRAINT AVG_POWER_PK PRIMARY KEY ( AVG_POWER_ID ) ;



CREATE INDEX LNG_TERM_TREND_DATA_IDXv1 ON LNG_TERM_TREND_DATA 
    ( 
     DATA_PNT_CAN_ID ASC 
    ) 
;
CREATE INDEX LNG_TERM_TREND_DATA_IDXv2 ON LNG_TERM_TREND_DATA 
    ( 
     TSTAMP ASC 
    ) 
;

ALTER TABLE LNG_TERM_TREND_DATA 
    ADD CONSTRAINT LNG_TERM_TREND_DATA_PK PRIMARY KEY ( LNG_TERM_TREND_DATA_ID ) ;



CREATE TABLE MED_TERM_TREND_DATA 
    ( 
     MED_TERM_TREND_DATA_ID BIGSERIAL NOT NULL , 
     DATA_PNT_CAN_ID INTEGER , 
     TSTAMP TIMESTAMPTZ , 
     EXTD BOOLEAN , 
     RTR BOOLEAN , 
     DATA_LEN INTEGER , 
     FVAL DOUBLE PRECISION , 
     IVAL INTEGER , 
     CVAL VARCHAR (16) , 
     STATE VARCHAR (10) CHECK ( STATE IN ('HighErr', 'HighWarn', 'LowErr', 'LowWarn', 'Normal')) 
    ) 
;


CREATE INDEX MED_TERM_TREND_DATA_IDXv1 ON MED_TERM_TREND_DATA 
    ( 
     DATA_PNT_CAN_ID ASC 
    ) 
;
CREATE INDEX MED_TERM_TREND_DATA_IDXv2 ON MED_TERM_TREND_DATA 
    ( 
     TSTAMP ASC 
    ) 
;

ALTER TABLE MED_TERM_TREND_DATA 
    ADD CONSTRAINT MED_TERM_TREND_DATA_PK PRIMARY KEY ( MED_TERM_TREND_DATA_ID ) ;



CREATE TABLE MSRMNT 
    ( 
     MSRMNT_ID BIGINT NOT NULL , 
     MSRMNT_NAME VARCHAR (255) , 
     CAN_ID INTEGER UNIQUE, 
     DEV_ID_FK BIGINT NOT NULL , 
     DEV_TYPE_ID_FK BIGINT NOT NULL , 
     REPRTNG_FRQ INTEGER , 
     MSRMNT_TYPE VARCHAR 
    ) 
;


CREATE INDEX MSRMNT__IDXv1 ON MSRMNT 
    ( 
     CAN_ID ASC 
    ) 
;

ALTER TABLE MSRMNT 
    ADD CONSTRAINT MSRMNT_PK PRIMARY KEY ( MSRMNT_ID ) ;




CREATE TABLE MSRMNT_TYPE 
    (      
     MSRMNT_TYPE_ID BIGINT NOT NULL , 
     MSRMNT_TYPE VARCHAR (255) , 
     LOW_ERR_THRHLD DOUBLE PRECISION , 
     LOW_WRNG_THRHLD DOUBLE PRECISION , 
     HIGH_ERR_THRHLD DOUBLE PRECISION , 
     HIGH_WRNG_THRHLD DOUBLE PRECISION , 
     REPRTNG_FRQ INTEGER 
    ) 
;


CREATE INDEX MSRMNT_TYPE__IDXv1 ON MSRMNT_TYPE
    ( 
     MSRMNT_TYPE_ID ASC 
    ) 
;

ALTER TABLE MSRMNT_TYPE 
    ADD CONSTRAINT MSRMNT_TYPE__PK PRIMARY KEY ( MSRMNT_TYPE_ID ) ;



CREATE TABLE MSRMNT_DATA 
    ( 
     MSRMNT_DATA_ID BIGINT NOT NULL , 
     DATA_PNT_CAN_ID INTEGER NOT NULL , 
     TSTAMP TIMESTAMPTZ , 
     EXTD BOOLEAN , 
     RTR BOOLEAN , 
     DATA_LEN INTEGER , 
     FVAL DOUBLE PRECISION , 
     IVAL INTEGER , 
     CVAL VARCHAR (16) , 
     STATE VARCHAR (10) CHECK ( STATE IN ('HighErr', 'HighWarn', 'LowErr', 'LowWarn', 'Normal')) 
    ) 
;


CREATE INDEX MSRMNT_DATA_IDXv1 ON MSRMNT_DATA 
    ( 
     DATA_PNT_CAN_ID ASC 
    ) 
;
CREATE INDEX MSRMNT_DATA_IDXv2 ON MSRMNT_DATA 
    ( 
     TSTAMP ASC 
    ) 
;

ALTER TABLE MSRMNT_DATA 
    ADD CONSTRAINT MSRMNT_DATA_PK PRIMARY KEY ( MSRMNT_DATA_ID ) ;



CREATE TABLE SHT_TERM_TREND_DATA 
    ( 
     SHT_TERM_TREND_DATA_ID BIGSERIAL NOT NULL , 
     DATA_PNT_CAN_ID INTEGER NOT NULL , 
     TSTAMP TIMESTAMPTZ , 
     EXTD BOOLEAN , 
     RTR BOOLEAN , 
     DATA_LEN INTEGER , 
     FVAL DOUBLE PRECISION , 
     IVAL INTEGER , 
     CVAL VARCHAR (16) , 
     STATE VARCHAR (10) CHECK ( STATE IN ('HighErr', 'HighWarn', 'LowErr', 'LowWarn', 'Normal')) 
    ) 
;


CREATE INDEX SHT_TERM_TREND_DATA_IDXv1 ON SHT_TERM_TREND_DATA 
    ( 
     DATA_PNT_CAN_ID ASC 
    ) 
;
CREATE INDEX SHT_TERM_TREND_DATA_IDXv2 ON SHT_TERM_TREND_DATA 
    ( 
     TSTAMP ASC 
    ) 
;

ALTER TABLE SHT_TERM_TREND_DATA 
    ADD CONSTRAINT SHT_TERM_TREND_DATA_PK PRIMARY KEY ( SHT_TERM_TREND_DATA_ID ) ;




ALTER TABLE MSRMNT 
    ADD CONSTRAINT DEV_MSRMNT FOREIGN KEY 
    ( 
     DEV_ID_FK
    ) 
    REFERENCES DEV 
    ( 
     DEV_ID
    ) 
;


ALTER TABLE DATA_PNT 
    ADD CONSTRAINT MSRMNT_DATA_PNT FOREIGN KEY 
    ( 
     MSRMNT_ID_FK
    ) 
    REFERENCES MSRMNT 
    ( 
     MSRMNT_ID
    ) 
;


ALTER TABLE MSRMNT 
    ADD CONSTRAINT MSRMNT_DEV_TYPE FOREIGN KEY 
    ( 
     DEV_TYPE_ID_FK
    ) 
    REFERENCES DEV_TYPE 
    ( 
     DEV_TYPE_ID
    ) 
;

CREATE OR REPLACE VIEW ALERTS AS
SELECT t1.DATA_PNT_CAN_ID,
  t1.TSTAMP,
  t1.FVAL,
  t1.IVAL,
  t1.CVAL,
  t1.STATE
FROM MSRMNT_DATA t1
WHERE t1.TSTAMP =
  (SELECT MAX(t2.TSTAMP)
  FROM MSRMNT_DATA t2
  WHERE t1.DATA_PNT_CAN_ID = t2.DATA_PNT_CAN_ID
  )
AND t1.STATE != 'Normal' ;



CREATE OR REPLACE VIEW all_trend_data AS 
        (         SELECT sht_term_trend_data.sht_term_trend_data_id,
                    sht_term_trend_data.data_pnt_can_id,
                    sht_term_trend_data.tstamp,
                    sht_term_trend_data.extd,
                    sht_term_trend_data.rtr,
                    sht_term_trend_data.data_len,
                    sht_term_trend_data.fval,
                    sht_term_trend_data.ival,
                    sht_term_trend_data.cval,
                    sht_term_trend_data.state
                   FROM sht_term_trend_data
        UNION
                 SELECT med_term_trend_data.med_term_trend_data_id AS sht_term_trend_data_id,
                    med_term_trend_data.data_pnt_can_id,
                    med_term_trend_data.tstamp,
                    med_term_trend_data.extd,
                    med_term_trend_data.rtr,
                    med_term_trend_data.data_len,
                    med_term_trend_data.fval,
                    med_term_trend_data.ival,
                    med_term_trend_data.cval,
                    med_term_trend_data.state
                   FROM med_term_trend_data)
UNION
         SELECT lng_term_trend_data.lng_term_trend_data_id AS sht_term_trend_data_id,
            lng_term_trend_data.data_pnt_can_id,
            lng_term_trend_data.tstamp,
            lng_term_trend_data.extd,
            lng_term_trend_data.rtr,
            lng_term_trend_data.data_len,
            lng_term_trend_data.fval,
            lng_term_trend_data.ival,
            lng_term_trend_data.cval,
            lng_term_trend_data.state
           FROM lng_term_trend_data;

ALTER TABLE all_trend_data
  OWNER TO teamarrow;


CREATE OR REPLACE VIEW all_trend_data_summary AS 
 SELECT ct.tstamp,
    ct."Velocity",
    ct."Battery_Ah",
    ct."Battery_SOC",
    ct."Bus_mA",
    ct."Bus_V",
    ct."Array1_mA",
    ct."Array1_V",
    ct."Array2_mA",
    ct."Array2_V",
    ct."Array3_mA",
    ct."Array3_V",
    ct."Wind_Speed",
    ct."Wind_Direction",
    ct."Latitiude",
    ct."Longitude",
    ct."Setpoint",
    ct."Irradiance"
   FROM crosstab('
	SELECT date_trunc(''second'',tstamp) as tstamp, data_pnt_can_id, fval
		FROM all_trend_data
		where data_pnt_can_id in (16436,28484,28480,16420,16416,28692,28688,28756,28752,28820,28816,12852,13076,13072,20500,12884)
		ORDER  BY 1,2 '::text, 'VALUES (''16436''), (''28484''), (''28480''),(''16420''::int), (''16416''), (''28692''), (''28688''), (''28756''), (''28752''), (''28820''), (''28816''), (''12852''), (''12848''), (''13076''), (''13072''), (''20500''), (''12884'')  '::text) ct(tstamp timestamp without time zone, "Velocity" double precision, "Battery_SOC" double precision, "Battery_Ah" double precision, "Bus_mA" double precision, "Bus_V" double precision, "Array1_mA" double precision, "Array1_V" double precision, "Array2_mA" double precision, "Array2_V" double precision, "Array3_mA" double precision, "Array3_V" double precision, "Wind_Speed" double precision, "Wind_Direction" double precision, "Latitiude" double precision, "Longitude" double precision, "Setpoint" double precision, "Irradiance" double precision);

ALTER TABLE all_trend_data_summary
  OWNER TO teamarrow;

CREATE OR REPLACE VIEW lng_term_trend_data_summary AS 
SELECT ct.tstamp,
    ct."Velocity",
    ct."Battery_Ah",
    ct."Battery_SOC",
    ct."Bus_mA",
    ct."Bus_V",
    ct."Array1_mA",
    ct."Array1_V",
    ct."Array2_mA",
    ct."Array2_V",
    ct."Array3_mA",
    ct."Array3_V",
    ct."Wind_Speed",
    ct."Wind_Direction",
    ct."Latitiude",
    ct."Longitude",
    ct."Setpoint",
    ct."Irradiance"
   FROM crosstab('
	SELECT date_trunc(''second'',tstamp) as tstamp, data_pnt_can_id, fval
		FROM lng_term_trend_data
		where data_pnt_can_id in (16436,28484,28480,16420,16416,28692,28688,28756,28752,28820,28816,12852,13076,13072,20500,12884)
		ORDER  BY 1,2 '::text, 'VALUES (''16436''), (''28484''), (''28480''),(''16420''::int), (''16416''), (''28692''), (''28688''), (''28756''), (''28752''), (''28820''), (''28816''), (''12852''), (''12848''), (''13076''), (''13072''), (''20500''), (''12884'')  '::text) ct(tstamp timestamp without time zone, "Velocity" double precision, "Battery_SOC" double precision, "Battery_Ah" double precision, "Bus_mA" double precision, "Bus_V" double precision, "Array1_mA" double precision, "Array1_V" double precision, "Array2_mA" double precision, "Array2_V" double precision, "Array3_mA" double precision, "Array3_V" double precision, "Wind_Speed" double precision, "Wind_Direction" double precision, "Latitiude" double precision, "Longitude" double precision, "Setpoint" double precision, "Irradiance" double precision);

ALTER TABLE lng_term_trend_data_summary
  OWNER TO teamarrow;

CREATE OR REPLACE VIEW med_term_trend_data_summary AS 
SELECT ct.tstamp,
    ct."Velocity",
    ct."Battery_Ah",
    ct."Battery_SOC",
    ct."Bus_mA",
    ct."Bus_V",
    ct."Array1_mA",
    ct."Array1_V",
    ct."Array2_mA",
    ct."Array2_V",
    ct."Array3_mA",
    ct."Array3_V",
    ct."Wind_Speed",
    ct."Wind_Direction",
    ct."Latitiude",
    ct."Longitude",
    ct."Setpoint",
    ct."Irradiance"
   FROM crosstab('
	SELECT date_trunc(''second'',tstamp) as tstamp, data_pnt_can_id, fval
		FROM med_term_trend_data
		where data_pnt_can_id in (16436,28484,28480,16420,16416,28692,28688,28756,28752,28820,28816,12852,13076,13072,20500,12884)
		ORDER  BY 1,2 '::text, 'VALUES (''16436''), (''28484''), (''28480''),(''16420''::int), (''16416''), (''28692''), (''28688''), (''28756''), (''28752''), (''28820''), (''28816''), (''12852''), (''12848''), (''13076''), (''13072''), (''20500''), (''12884'')  '::text) ct(tstamp timestamp without time zone, "Velocity" double precision, "Battery_SOC" double precision, "Battery_Ah" double precision, "Bus_mA" double precision, "Bus_V" double precision, "Array1_mA" double precision, "Array1_V" double precision, "Array2_mA" double precision, "Array2_V" double precision, "Array3_mA" double precision, "Array3_V" double precision, "Wind_Speed" double precision, "Wind_Direction" double precision, "Latitiude" double precision, "Longitude" double precision, "Setpoint" double precision, "Irradiance" double precision);

ALTER TABLE med_term_trend_data_summary
  OWNER TO teamarrow;

CREATE OR REPLACE VIEW sht_term_trend_data_summary AS 
SELECT ct.tstamp,
    ct."Velocity",
    ct."Battery_Ah",
    ct."Battery_SOC",
    ct."Bus_mA",
    ct."Bus_V",
    ct."Array1_mA",
    ct."Array1_V",
    ct."Array2_mA",
    ct."Array2_V",
    ct."Array3_mA",
    ct."Array3_V",
    ct."Wind_Speed",
    ct."Wind_Direction",
    ct."Latitiude",
    ct."Longitude",
    ct."Setpoint",
    ct."Irradiance"
   FROM crosstab('
	SELECT date_trunc(''second'',tstamp) as tstamp, data_pnt_can_id, fval
		FROM sht_term_trend_data
		where data_pnt_can_id in (16436,28484,28480,16420,16416,28692,28688,28756,28752,28820,28816,12852,13076,13072,20500,12884)
		ORDER  BY 1,2 '::text, 'VALUES (''16436''), (''28484''), (''28480''),(''16420''::int), (''16416''), (''28692''), (''28688''), (''28756''), (''28752''), (''28820''), (''28816''), (''12852''), (''12848''), (''13076''), (''13072''), (''20500''), (''12884'')  '::text) ct(tstamp timestamp without time zone, "Velocity" double precision, "Battery_SOC" double precision, "Battery_Ah" double precision, "Bus_mA" double precision, "Bus_V" double precision, "Array1_mA" double precision, "Array1_V" double precision, "Array2_mA" double precision, "Array2_V" double precision, "Array3_mA" double precision, "Array3_V" double precision, "Wind_Speed" double precision, "Wind_Direction" double precision, "Latitiude" double precision, "Longitude" double precision, "Setpoint" double precision, "Irradiance" double precision);

ALTER TABLE sht_term_trend_data_summary
  OWNER TO teamarrow;


CREATE OR REPLACE VIEW sht_and_med_term_trend_data AS 
 SELECT sht_term_trend_data.sht_term_trend_data_id,
    sht_term_trend_data.data_pnt_can_id,
    sht_term_trend_data.tstamp,
    sht_term_trend_data.extd,
    sht_term_trend_data.rtr,
    sht_term_trend_data.data_len,
    sht_term_trend_data.fval,
    sht_term_trend_data.ival,
    sht_term_trend_data.cval,
    sht_term_trend_data.state
   FROM sht_term_trend_data;

ALTER TABLE sht_and_med_term_trend_data
  OWNER TO teamarrow;


CREATE OR REPLACE VIEW sht_and_med_term_trend_data_summary AS 
 SELECT ct.tstamp,
    ct."Velocity",
    ct."Battery_Ah",
    ct."Battery_SOC",
    ct."Bus_mA",
    ct."Bus_V",
    ct."Array1_mA",
    ct."Array1_V",
    ct."Array2_mA",
    ct."Array2_V",
    ct."Array3_mA",
    ct."Array3_V",
    ct."Wind_Speed",
    ct."Wind_Direction",
    ct."Latitiude",
    ct."Longitude",
    ct."Setpoint",
    ct."Irradiance"
   FROM crosstab('
	SELECT date_trunc(''second'',tstamp) as tstamp, data_pnt_can_id, fval
		FROM sht_and_med_term_trend_data
		where data_pnt_can_id in (16436,28484,28480,16420,16416,28692,28688,28756,28752,28820,28816,12852,13076,13072,20500,12884)
		ORDER  BY 1,2 '::text, 'VALUES (''16436''), (''28484''), (''28480''),(''16420''::int), (''16416''), (''28692''), (''28688''), (''28756''), (''28752''), (''28820''), (''28816''), (''12852''), (''12848''), (''13076''), (''13072''), (''20500''), (''12884'')  '::text) ct(tstamp timestamp without time zone, "Velocity" double precision, "Battery_SOC" double precision, "Battery_Ah" double precision, "Bus_mA" double precision, "Bus_V" double precision, "Array1_mA" double precision, "Array1_V" double precision, "Array2_mA" double precision, "Array2_V" double precision, "Array3_mA" double precision, "Array3_V" double precision, "Wind_Speed" double precision, "Wind_Direction" double precision, "Latitiude" double precision, "Longitude" double precision, "Setpoint" double precision, "Irradiance" double precision);

ALTER TABLE sht_and_med_term_trend_data_summary
  OWNER TO teamarrow;


CREATE OR REPLACE VIEW all_trend_data_with_deviceinfo AS 
 SELECT trend_data.tstamp + '10:00:00'::interval AS "BNE_time",
    dev.dev_name,
    msrmnt.msrmnt_name,
    data_pnt.name,
    trend_data.data_pnt_can_id,
    trend_data.fval,
    trend_data.ival,
    trend_data.cval
   FROM all_trend_data trend_data
   JOIN data_pnt ON trend_data.data_pnt_can_id = data_pnt.data_pnt_can_id
   JOIN msrmnt ON msrmnt.msrmnt_id = data_pnt.msrmnt_id_fk
   JOIN dev ON dev.dev_id = msrmnt.dev_id_fk
  ORDER BY trend_data.tstamp, trend_data.data_pnt_can_id;

ALTER TABLE all_trend_data_with_deviceinfo
  OWNER TO teamarrow;


CREATE OR REPLACE VIEW splunk_lookup_data AS
 SELECT data_pnt.data_pnt_can_id,
    data_pnt.name AS data_pnt_name,
    dev.dev_name,
    msrmnt.msrmnt_name
   FROM data_pnt
     JOIN msrmnt ON data_pnt.msrmnt_id_fk = msrmnt.msrmnt_id
     JOIN dev ON msrmnt.dev_id_fk = dev.dev_id;

ALTER TABLE splunk_lookup_data
  OWNER TO teamarrow;



CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1; 


-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                             8
-- CREATE INDEX                            10
-- ALTER TABLE                             12
-- CREATE VIEW                              1
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE STRUCTURED TYPE                   0
-- CREATE COLLECTION TYPE                   0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
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

insert into dev_type(DEV_TYPE_ID, DEV_TYPE) values (1, 'Analog');
insert into dev_type(DEV_TYPE_ID, DEV_TYPE) values (2, 'Binary');

insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (1, 'Wave Sculptor', 'WS22');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (2, 'Driver Controller', 'EVDC');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (3, 'Battery Management System', 'BMS');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (4, 'Max Power Point Tracker 1', 'MPPT1');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (5, 'Max Power Point Tracker 2', 'MPPT2');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (6, 'Max Power Point Tracker 3', 'MPPT3');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (7, 'Weather Station', 'WEATHER');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (8, 'Global Positioning System', 'GPS');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (9, 'Telemetry Calculated Data', 'TELEMETRY');
insert into dev(DEV_ID, DEV_NAME, DEV_ABBREV) values (10, 'BMS DC-DC Board', 'BMS-DC-DC');

insert into msrmnt_type(MSRMNT_TYPE_ID, MSRMNT_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_ERR_THRHLD , HIGH_WRNG_THRHLD , REPRTNG_FRQ) values (1, 'BatteryVoltage',10, 20, 100, 110, 5);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (1, 'Battery Management System CMU 0 Status', 'BMS_CMU_Sts', hex_to_int('601'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (2, 'Battery Management System CMU 1 Status', 'BMS_CMU_Sts' , hex_to_int('604'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (3, 'Battery Management System CMU 2 Status', 'BMS_CMU_Sts' , hex_to_int('607'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (4, 'Battery Management System CMU 3 Status', 'BMS_CMU_Sts' , hex_to_int('60A'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (5, 'Battery Management System CMU 4 Status', 'BMS_CMU_Sts' , hex_to_int('60D'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (6, 'Battery Management System Cell 0-3 Voltage (CMU 0)', 'BMS_Cell_Voltage', hex_to_int('602'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (7, 'Battery Management System Cell 4-7 Voltage (CMU 0)', 'BMS_Cell_Voltage', hex_to_int('603'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (8, 'Battery Management System Cell 0-3 Voltage (CMU 1)', 'BMS_Cell_Voltage', hex_to_int('605'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (9, 'Battery Management System Cell 4-7 Voltage (CMU 1)', 'BMS_Cell_Voltage', hex_to_int('606'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (10, 'Battery Management System Cell 0-3 Voltage (CMU 2)', 'BMS_Cell_Voltage', hex_to_int('608'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (11, 'Battery Management System Cell 4-7 Voltage (CMU 2)', 'BMS_Cell_Voltage', hex_to_int('609'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (12, 'Battery Management System Cell 0-3 Voltage (CMU 3)', 'BMS_Cell_Voltage', hex_to_int('60B'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (13, 'Battery Management System Cell 4-7 Voltage (CMU 3)', 'BMS_Cell_Voltage', hex_to_int('60C'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (14, 'Battery Management System Cell 0-3 Voltage (CMU 4)', 'BMS_Cell_Voltage', hex_to_int('60E'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (15, 'Battery Management System Cell 4-7 Voltage (CMU 4)', 'BMS_Cell_Voltage', hex_to_int('60F'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (16, 'Battery Management System Charger Control', 'BMS_Charger_Control', hex_to_int('6F6'), 3, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (17, 'Battery Management System Device Info', 'BMS_Device_Info', hex_to_int('600'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (18, 'Battery Management System Ext Pack Status', 'BMS_Ext_Pack_Sts', hex_to_int('6FD'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (19, 'Battery Management System Fan Status', 'BMS_Fan_Sts', hex_to_int('6FC'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (20, 'Battery Management System Min Max Cell Voltage', 'BMS_Min_Max_Cell_Voltage', hex_to_int('6F8'), 3, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (21, 'Battery Management System Min Max Cell Temp', 'BMS_Min_Max_Cell_Temp', hex_to_int('6F9'), 3, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (22, 'Battery Management System Pack Balance', 'BMS_Pack_Balance', hex_to_int('6F5'), 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (23, 'Battery Management System Pack Status', 'BMS_Pack_Sts', hex_to_int('6FB'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (24, 'Battery Management System Pack Voltage Current', 'BMS_Pack_Voltage_Current', hex_to_int('6FA'), 3, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (25, 'Battery Management System Precharge Status', 'BMS_Precharge_Sts', hex_to_int('6F7'), 3, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (26, 'Battery Management System State of Charge', 'BMS_SOC', hex_to_int('6F4'), 3, 1, 1);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (27, 'Driver Controller Device info', 'EVDC_Device_info', 1280, 2, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (28, 'Driver Controller Motor Drive Command', 'EVDC_Motor_Drive_Cmd', 1281, 2, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (29, 'Driver Controller Motor Power Command', 'EVDC_Motor_Power_Cmd', 1282, 2, 1, 10);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (30, 'Driver Controller Switch Positions', 'EVDC_Switch_Pos', 1285, 2, 2, 10);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (31, 'Max Power Point Tracker 1 Power', 'MPPT_Power', hex_to_int('701'), 4, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (32, 'Max Power Point Tracker 1 Status', 'MPPT_Status', hex_to_int('702'), 4, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (33, 'Max Power Point Tracker 2 Power', 'MPPT_Power', hex_to_int('705'), 5, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (34, 'Max Power Point Tracker 2 Status', 'MPPT_Status', hex_to_int('706'), 5, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (35, 'Max Power Point Tracker 3 Power', 'MPPT_Power', hex_to_int('709'), 6, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (36, 'Max Power Point Tracker 3 Status', 'MPPT_Status', hex_to_int('70A'), 6, 1, 1);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (37, 'Wave Sculptor Bus Measurement', 'WS22_Bus_Measurement', 1026, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (38, 'Wave Sculptor DSP Supply Voltage', 'WS22_DSP_Supply_Voltage', 1033, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (39, 'Wave Sculptor DSP Temp', 'WS22_DSP_Temp', 1036, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (40, 'Wave Sculptor Device Info', 'WS22_Device_Info', 1024, 1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (41, 'Wave Sculptor Energy and Odometer', 'WS22_Energy_Odo', 1038, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (42, 'Wave Sculptor Heat Motor Temp', 'WS22_Heat_Motor_Temp', 1035, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (43, 'Wave Sculptor Motor Back EMF', 'WS22_Motor_Back_EMF', 1031, 1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (44, 'Wave Sculptor Motor Current', 'WS22_Motor_Current', 1030,  1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (45, 'Wave Sculptor Motor Voltage', 'WS22_Motor_Voltage', 1029, 1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (46, 'Wave Sculptor Phase Current', 'WS22_Phase_Current', 1028,  1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (47, 'Wave Sculptor Status Info', 'WS22_Status_Info', 1025, 1, 1, 5);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (48, 'Wave Sculptor Supply Voltage', 'WS22_Supply_Voltage', 1032, 1, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (49, 'Wave Sculptor Velocity', 'WS22_Velocity', 1027, 1, 1, 5);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (52, 'Weather Station Temp', 'Weather_Temp', hex_to_int('321'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (53, 'Weather Station Humidity', 'Weather_Humidity', hex_to_int('322'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (54, 'Weather Station Wind', 'Weather_Wind', hex_to_int('323'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (55, 'Weather Station Compass', 'Weather_Compass', hex_to_int('324'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (56, 'Weather Station Radiation', 'Weather_Radiation', hex_to_int('325'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (57, 'Weather Station Radiation Range', 'Weather_Radiation_Range', hex_to_int('326'), 7, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (58, 'Weather Station Air', 'Weather_Air', hex_to_int('327'), 7, 1, 1);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (59, 'GPS Location', 'GPS_Location', hex_to_int('331'), 8, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (60, 'GPS Speed / Direction', 'GPS_Speed_Direction', hex_to_int('332'), 8, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (61, 'GPS Altitude / Satelite Nos', 'GPS_Alt_Sats', hex_to_int('333'), 8, 1, 1);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (62, 'Array 1 Power', 'Array_1_Power', hex_to_int('341'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (63, 'Array 2 Power', 'Array_2_Power', hex_to_int('342'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (64, 'Array 3 Power', 'Array_3_Power', hex_to_int('343'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (65, 'Total Array Power', 'Total_Array_Power', hex_to_int('344'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (66, 'Bus Power', 'Bus_Power', hex_to_int('345'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (67, 'Net Power Position', 'Net_Power_Position', hex_to_int('346'), 9, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (68, 'Distance', 'Distance', hex_to_int('347'), 9, 1, 1);

insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (80, 'BMS DC-DC Heartbeat', 'DC-DC_Heartbeat', hex_to_int('100'), 10, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (81, 'BMS DC-DC Cell Temp', 'DC-DC_Cell_Temp', hex_to_int('103'), 10, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (82, 'BMS DC-DC Cell Voltages', 'DC-DC_Cell_Voltages', hex_to_int('104'), 10, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (83, 'BMS DC-DC Cell Currents', 'DC-DC_Cell_Currents', hex_to_int('105'), 10, 1, 1);
insert into msrmnt(MSRMNT_ID, MSRMNT_NAME, MSRMNT_TYPE, CAN_ID, DEV_ID_FK, DEV_TYPE_ID_FK, REPRTNG_FRQ) values (84, 'BMS DC-DC Status Info', 'DC-DC_Status_Info', hex_to_int('106'), 10, 1, 1);

-- DC-DC
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (1, hex_to_int('1004'), 'Flash Serial', 'Flash Serial', 4,  4 ,'int', 0, 0, 0, 0, 80, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (2, hex_to_int('1000'), 'Device Id', 'Device Id', 4,  0 ,'int', 0, 0, 0, 0, 80, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (3, hex_to_int('1030'), 'Cell Temp', 'Cell Temp', 2,  0 ,'int', 0, 0, 0, 0, 81, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (4, hex_to_int('1046'), 'Cell 4 Voltage', 'Cell 1 Voltage', 2,  6 ,'int', 0, 0, 0, 0, 82, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (5, hex_to_int('1044'), 'Cell 3 Voltage', 'Cell 2 Voltage', 2,  4 ,'int', 0, 0, 0, 0, 82, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (6, hex_to_int('1042'), 'Cell 2 Voltage', 'Cell 3 Voltage', 2,  2 ,'int', 0, 0, 0, 0, 82, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (7, hex_to_int('1040'), 'Cell 1 Voltage', 'Cell 4 Voltage', 2,  0 ,'int', 0, 0, 0, 0, 82, 'mv');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (8, hex_to_int('1054'), 'Net 12v Current', 'Net 12v Current', 4,  4 ,'int', 0, 0, 0, 0, 83, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (9, hex_to_int('1050'), 'HV DC-DC Curent', 'Device Id', 4,  0 ,'int', 0, 0, 0, 0, 83, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (10, hex_to_int('1062'), 'Status Flags', 'Status Flags', 2,  2 ,'int', 0, 0, 0, 0, 84, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (11, hex_to_int('1060'), 'Status Events', 'Status Events', 0,  0 ,'int', 0, 0, 0, 0, 84, 'NA');

-- Environmental Systems
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (101, hex_to_int('3214'), 'Windchill Temp', 'Windchill Temp (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 52, 'C');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (102, hex_to_int('3210'), 'Air Temp', 'Air Temp (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 52, 'C');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (103, hex_to_int('3224'), 'Relative Humidity', 'Relative Humidity (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 53, '%');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (104, hex_to_int('3220'), 'Air Pressure', 'Air Pressure (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 53, 'hpa');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (105, hex_to_int('3234'), 'Wind Speed', 'Wind Speed (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 54, 'm/s');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (106, hex_to_int('3230'), 'Wind Direction', 'Wind Direction (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 54, 'Deg');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (107, hex_to_int('3240'), 'Compass Heading', 'Compass Heading (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 55, 'Deg');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (108, hex_to_int('3254'), 'Actual Global Radiation', 'Actual Global Radiation (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 56, 'W/m2');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (109, hex_to_int('3250'), 'Average Global Radiation', 'Actual Global Radiation (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 56, 'W/m2');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (110, hex_to_int('3264'), 'Min Global Radiation', 'Min Global Radiation (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 57, 'W/m2');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (111, hex_to_int('3260'), 'Max Global Radiation', 'Max Global Radiation (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 57, 'W/m2');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (112, hex_to_int('3270'), 'Air Density', 'Air Density (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 58, 'kg/m3');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (113, hex_to_int('3310'), 'Latitude', 'Latitude (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 59, 'Deg');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (114, hex_to_int('3314'), 'Longitude', 'Longitude (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 59, 'Deg');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (115, hex_to_int('3320'), 'Velocity', 'Velocity (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 60, 'm/sec');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (116, hex_to_int('3324'), 'Direction', 'Direction (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 60, 'Deg');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (117, hex_to_int('3330'), 'Altitude', 'Altitude (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 61, 'm');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (118, hex_to_int('3334'), 'Visible Satelites', 'Visible Satelites (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 61, 'NA');

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (201, hex_to_int('3410'), 'Array 1 Power', 'Array 1 Power (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 62, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (202, hex_to_int('3420'), 'Array 2 Power', 'Array 2 Power (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 63, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (203, hex_to_int('3430'), 'Array 3 Power', 'Array 3 Power (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 64, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (204, hex_to_int('3440'), 'Total Array Power', 'Total Array Power (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 65, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (205, hex_to_int('3450'), 'Total Bus Power', 'Total Bus Power (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 66, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (206, hex_to_int('3460'), 'Net Power Position', 'Net Power Position (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 67, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (207, hex_to_int('3470'), 'Distance Travelled', 'Distance Travelled (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 68, 'm');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (208, hex_to_int('3474'), 'Distance Remaining', 'Distance Remaining (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 68, 'm');

-- WaveSculpter - 1 

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (301, hex_to_int('4004'), 'Serial Number', 'Serial Number (Uint32)', 4,  4 ,'int', 0, 0, 0, 0, 40, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (302, hex_to_int('4000'), 'Device ID', 'Device ID (char[4])', 4,  0 ,'Char', 0, 0, 0, 0, 40, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (303, hex_to_int('4017'), 'CAN Rx Errors (Uint8)', 'CAN Rx Errors (Uint8)', 1,  7 ,'int', 0, 0, 0, 0, 47, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (304, hex_to_int('4016'), 'CAN Tx Errors', 'CAN Tx Errors (Uint8)', 1,  6 ,'int', 0, 0, 0, 0, 47, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (305, hex_to_int('4014'), 'Active Motor Index', 'Active Motor Index (Uint16)', 2,  4 ,'int', 0, 0, 0, 0, 47, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (306, hex_to_int('4012'), 'Error Flags', 'Error Flags (Uint16)', 2,  2 ,'int', 0, 0, 0, 0, 47, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (307, hex_to_int('4010'), 'Limiting Flags', 'Limiting Flags (Uint16)', 2,  0 ,'int', 0, 0, 0, 0, 47, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (308, hex_to_int('4024'), 'Bus Current', 'Bus Current (Float)', 4,  4 ,'Float', 0.1, 0.2, 0.1, 0.2, 37, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (309, hex_to_int('4020'), 'Bus Voltage', 'Bus Voltage (Float)', 4,  0 ,'Float', 1, 10, 90, 100, 37, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (310, hex_to_int('4034'), 'Vehicle Velocity m/s', 'Vehicle Velocity m/s (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 49, 'm/s');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (311, hex_to_int('4030'), 'Motor rpm', 'Motor rpm (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 49, 'RPM');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (312, hex_to_int('4044'), 'Phase C RMS current', 'Phase C RMS current (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 46, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (313, hex_to_int('4040'), 'Phase B RMS current', 'Phase B RMS current (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 46, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (314, hex_to_int('4054'), 'Real-axis motor voltage vector', 'Real-axis motor voltage vector (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 45, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (315, hex_to_int('4050'), 'Imaginary-axis motor voltage vector', 'Imaginary-axis motor voltage vector (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 45, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (316, hex_to_int('4064'), 'Real-axis motor current vector', 'Real-axis motor current vector (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 44, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (317, hex_to_int('4060'), 'Imaginary-axis motor current vector', 'Imaginary-axis motor current vector (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 44, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (318, hex_to_int('4070'), 'Peak phase-neutral motor voltage estimation', 'Peak phase-neutral motor voltage estimation (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 43, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (319, hex_to_int('4084'), '15V supply voltage', '15V supply voltage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 48, 'V');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (320, hex_to_int('4094'), '3.3V supply voltage', '3.3V supply voltage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 38, 'V');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (321, hex_to_int('4090'), '1.9V supply voltage', '1.9V supply voltage (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 38, 'V');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (322, hex_to_int('40B4'), 'WS22 heatsink temperature', 'WS22 heatsink temperature (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 42, 'Degrees');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (323, hex_to_int('40B0'), 'Motor temperature', 'Motor temperature (Float)', 4,  0 ,'Float', 0, 30, 100, 120, 42, 'Degrees');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (324, hex_to_int('40C0'), 'DSP operating temperature', 'DSP operating temperature (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 39, 'Degrees');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (325, hex_to_int('40E4'), 'Ah consumed by WS22 since last reset', 'Ah consumed by WS22 since last reset (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 41, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (326, hex_to_int('40E0'), 'Distance (metres) travelled', 'Distance (metres) vehicle has travelled since reset (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 41, 'm');

-- Driver Controls

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (401, hex_to_int('5004'), 'Serial Number', 'Serial Number (Uint32)', 4,  4 ,'int', 0, 0, 0, 0, 27, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (402, hex_to_int('5000'), 'Device ID', 'Device ID (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 27, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (403, hex_to_int('5014'), 'Motor current setpoint percentage', 'Motor current setpoint percentage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 28, '%');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (404, hex_to_int('5010'), 'Motor rpm setpoint', 'Motor rpm setpoint (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 28, 'RPM');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (405, hex_to_int('5024'), 'Bus current setpoint percentage', 'Bus current setpoint percentage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 29, '%');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (406, hex_to_int('5057'), 'State', 'State (Uint8)', 1,  7 ,'int', 0, 0, 0, 0, 30, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (407, hex_to_int('5056'), 'Flags', 'Flags (Uint8)', 1,  6 ,'int', 0, 0, 0, 0, 30, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (408, hex_to_int('5050'), 'Switch position bitfield', 'Switch position bitfield (Uint16)', 2,  0 ,'int', 0, 0, 0, 0, 30, 'NA');

-- Battery Management Systems 1

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (501, hex_to_int('6004'), 'Serial Number', 'Serial Number (Uint32)', 4,  4 ,'int', 0, 0, 0, 0, 17, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (502, hex_to_int('6000'), 'Device ID', 'Device ID (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 17, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (503, hex_to_int('6016'), 'Cell temperature', 'Cell temperature 1/10th °C (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 1, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (504, hex_to_int('6014'), 'CMU PCB temperature', 'CMU PCB temperature 1/10th °C (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 1, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (505, hex_to_int('6010'), 'CMU serial number', 'CMU serial number (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 1, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (506, hex_to_int('6046'), 'Cell temperature', 'Cell temperature 1/10th °C (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 2, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (507, hex_to_int('6044'), 'CMU PCB temperature', 'CMU PCB temperature 1/10th °C (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 2, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (508, hex_to_int('6040'), 'CMU serial number', 'CMU serial number (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 2, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (509, hex_to_int('6076'), 'Cell temperature', 'Cell temperature 1/10th °C (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 3, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (510, hex_to_int('6074'), 'CMU PCB temperature', 'CMU PCB temperature 1/10th °C (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 3, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (511, hex_to_int('6070'), 'CMU serial number', 'CMU serial number (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 3, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (512, hex_to_int('60A6'), 'Cell temperature', 'Cell temperature 1/10th °C (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 4, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (513, hex_to_int('60A4'), 'CMU PCB temperature', 'CMU PCB temperature 1/10th °C (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 4, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (514, hex_to_int('60A0'), 'CMU serial number', 'CMU serial number (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 4, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (515, hex_to_int('60D6'), 'Cell temperature', 'Cell temperature 1/10th °C (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 5, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (516, hex_to_int('60D4'), 'CMU PCB temperature', 'CMU PCB temperature 1/10th °C (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 5, '1/10th Deg Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (517, hex_to_int('60D0'), 'CMU serial number', 'CMU serial number (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 5, 'hA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (518, hex_to_int('6026'), 'Cell 3 Voltage', 'Cell 3 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 6, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (519, hex_to_int('6024'), 'Cell 2 Voltage', 'Cell 2 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 6, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (520, hex_to_int('6022'), 'Cell 1 Voltage', 'Cell 1 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 6, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (521, hex_to_int('6020'), 'Cell 0 Voltage', 'Cell 0 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 6, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (522, hex_to_int('6036'), 'Cell 7 Voltage', 'Cell 7 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 7, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (523, hex_to_int('6034'), 'Cell 6 Voltage', 'Cell 6 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 7, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (524, hex_to_int('6032'), 'Cell 5 Voltage', 'Cell 5 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 7, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (525, hex_to_int('6030'), 'Cell 4 Voltage', 'Cell 4 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 7, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (526, hex_to_int('6056'), 'Cell 3 Voltage', 'Cell 3 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 8, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (527, hex_to_int('6054'), 'Cell 2 Voltage', 'Cell 2 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 8, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (528, hex_to_int('6052'), 'Cell 1 Voltage', 'Cell 1 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 8, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (529, hex_to_int('6050'), 'Cell 0 Voltage', 'Cell 0 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 8, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (530, hex_to_int('6066'), 'Cell 7 Voltage', 'Cell 7 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 9, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (531, hex_to_int('6064'), 'Cell 6 Voltage', 'Cell 6 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 9, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (532, hex_to_int('6062'), 'Cell 5 Voltage', 'Cell 5 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 9, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (533, hex_to_int('6060'), 'Cell 4 Voltage', 'Cell 4 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 9, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (534, hex_to_int('6086'), 'Cell 3 Voltage', 'Cell 3 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 10, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (535, hex_to_int('6084'), 'Cell 2 Voltage', 'Cell 2 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 10, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (536, hex_to_int('6082'), 'Cell 1 Voltage', 'Cell 1 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 10, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (537, hex_to_int('6080'), 'Cell 0 Voltage', 'Cell 0 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 10, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (538, hex_to_int('6096'), 'Cell 7 Voltage', 'Cell 7 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 11, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (539, hex_to_int('6094'), 'Cell 6 Voltage', 'Cell 6 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 11, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (540, hex_to_int('6092'), 'Cell 5 Voltage', 'Cell 5 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 11, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (541, hex_to_int('6090'), 'Cell 4 Voltage', 'Cell 4 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 11, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (542, hex_to_int('60B6'), 'Cell 3 Voltage', 'Cell 3 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 12, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (543, hex_to_int('60B4'), 'Cell 2 Voltage', 'Cell 2 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 12, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (544, hex_to_int('60B2'), 'Cell 1 Voltage', 'Cell 1 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 12, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (545, hex_to_int('60B0'), 'Cell 0 Voltage', 'Cell 0 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 12, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (546, hex_to_int('60C6'), 'Cell 7 Voltage', 'Cell 7 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 13, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (547, hex_to_int('60C4'), 'Cell 6 Voltage', 'Cell 6 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 13, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (548, hex_to_int('60C2'), 'Cell 5 Voltage', 'Cell 5 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 13, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (549, hex_to_int('60C0'), 'Cell 4 Voltage', 'Cell 4 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 13, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (550, hex_to_int('60E6'), 'Cell 3 Voltage', 'Cell 3 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 14, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (551, hex_to_int('60E4'), 'Cell 2 Voltage', 'Cell 2 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 14, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (552, hex_to_int('60E2'), 'Cell 1 Voltage', 'Cell 1 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 14, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (553, hex_to_int('60E0'), 'Cell 0 Voltage', 'Cell 0 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 14, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (554, hex_to_int('60F6'), 'Cell 7 Voltage', 'Cell 7 mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 15, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (555, hex_to_int('60F4'), 'Cell 6 Voltage', 'Cell 6 mV (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 15, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (556, hex_to_int('60F2'), 'Cell 5 Voltage', 'Cell 5 mV (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 15, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (557, hex_to_int('60F0'), 'Cell 4 Voltage', 'Cell 4 mV (Int16)', 2,  0 ,'int', 0, 0, 0, 0, 15, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (558, hex_to_int('6F44'), 'SOC Percentage', 'SOC Percentage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 26, '%');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (559, hex_to_int('6F40'), 'SOC Ah', 'SOC Ah (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 26, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (560, hex_to_int('6F54'), 'Balance Percentage', 'Balance Percentage (Float)', 4,  4 ,'Float', 0, 0, 0, 0, 22, '%');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (561, hex_to_int('6F50'), 'Balance Ah', 'Balance Ah (Float)', 4,  0 ,'Float', 0, 0, 0, 0, 22, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (562, hex_to_int('6F66'), 'Charge cell voltage error', 'Charge cell voltage error mV (Int16)', 2,  6 ,'int', 0, 0, 0, 0, 16, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (563, hex_to_int('6F64'), 'Cell temperature margin', 'Cell temperature margin (Int16)', 2,  4 ,'int', 0, 0, 0, 0, 16, 'Degress Celcius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (564, hex_to_int('6F62'), 'Discharge cell voltage error', 'Discharge cell voltage error (Int16)', 2,  2 ,'int', 0, 0, 0, 0, 16, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (565, hex_to_int('6F60'), 'Total pack capacity', 'Total pack capacity Ah (Uint16)', 2,  0 ,'int', 0, 0, 0, 0, 16, 'W');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (566, hex_to_int('6F77'), 'Precharge timer', 'Precharge timer (Uint8)', 1,  7 ,'int', 0, 0, 0, 0, 25, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (567, hex_to_int('6F76'), 'Timer flag', 'Timer flag (Uint8)', 1,  6 ,'int', 0, 0, 0, 0, 25, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (568, hex_to_int('6F71'), 'Precharge State', 'Precharge State (Uint8)', 1,  1 ,'int', 0, 0, 0, 0, 25, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (569, hex_to_int('6F70'), 'Contactor status', 'Contactor status (Uint8)', 1,  0 ,'int', 0, 0, 0, 0, 25, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (570, hex_to_int('6F87'), 'Cell number max cell', 'Cell number max cell', 1,  7 ,'int', 0, 0, 0, 0, 20, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (571, hex_to_int('6F86'), 'CMU number max cell', 'CMU number max cell', 1,  6 ,'int', 0, 0, 0, 0, 20, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (572, hex_to_int('6F85'), 'Cell number min cell', 'Cell number min cell', 1,  5 ,'int', 0, 0, 0, 0, 20, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (573, hex_to_int('6F84'), 'CMU number min cell', 'CMU number min cell', 1,  4 ,'int', 0, 0, 0, 0, 20, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (574, hex_to_int('6F82'), 'Max cell voltage', 'Max cell voltage mV (Uint16)', 2,  2 ,'int', 0, 0, 0, 0, 20, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (575, hex_to_int('6F80'), 'Min cell voltage', 'Min cell voltage mV (Uint16)	', 2,  0 ,'int', 0, 0, 0, 0, 20, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (576, hex_to_int('6F96'), 'CMU number max temp', 'CMU number max temp', 1,  6 ,'int', 0, 0, 0, 0, 21, 'Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (577, hex_to_int('6F94'), 'CMU number min temp', 'CMU number min temp', 1,  4 ,'int', 0, 0, 0, 0, 21, 'Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (578, hex_to_int('6F92'), 'Max cell temperature', 'Max cell temperature 1/10th °C (Uint16)', 2,  2 ,'int', 0, 0, 0, 0, 21, '1/10th Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (579, hex_to_int('6F90'), 'Min cell temperature', 'Min cell temperature 1/10°C (Uint16)', 2,  0 ,'int', 0, 0, 0, 0, 21, '1/10th Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (580, hex_to_int('6FA0'), 'Battery voltage', 'Battery voltage mV (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 24, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (581, hex_to_int('6FA4'), 'Battery Current', 'Battery Current mA (Int32)', 4,  4 ,'int', 0, 0, 0, 0, 24, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (582, hex_to_int('6FB6'), 'BMU Firmware build number', 'BMU Firmware build number (Uint16)', 2,  6 ,'int', 0, 0, 0, 0, 23, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (583, hex_to_int('6FB5'), 'CMU count', 'CMU count (Uint8)', 1,  5 ,'int', 0, 0, 0, 0, 23, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (584, hex_to_int('6FB4'), 'Status Flags', 'Status Flags (Uint8)', 1,  4 ,'int', 0, 0, 0, 0, 23, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (585, hex_to_int('6FB2'), 'Balance voltage threshold falling mV', 'Balance voltage threshold falling mV (Uint16)', 2,  2 ,'int', 0, 0, 0, 0, 23, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (586, hex_to_int('6FB0'), 'Balance voltage threshold rising mV', 'Balance voltage threshold rising mV (Uint16)', 2,  0 ,'int', 0, 0, 0,  0,23, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (587, hex_to_int('6FC6'), '12V current CMUs', '12V current CMUs (Uint16)', 2,  6 ,'int', 0, 0, 0, 0, 19, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (588, hex_to_int('6FC4'), '12V current fans / contactors', '12V current fans / contactors (Uint16)', 2,  4 ,'int', 0, 0, 0, 0, 19, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (589, hex_to_int('6FC2'), 'Fan speed 1', 'Fan speed 1 rpm (Uint16)', 2,  2 ,'int', 0, 0, 0, 0, 19, 'RPM');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (590, hex_to_int('6FC0'), 'Fan speed 0', 'Fan speed 0 rpm (Uint16)', 2,  0 ,'int', 0, 0, 0, 0, 19, 'RPM');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (591, hex_to_int('6FD5'), 'BMU model ID', 'BMU model ID', 2,  5 ,'int', 0, 0, 0, 0, 18, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (592, hex_to_int('6FD4'), 'BMU hardware version', 'BMU hardware version', 2,  4 ,'int', 0, 0, 0, 0, 18, 'NA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (593, hex_to_int('6FD0'), 'Extended status flags', 'Extended status flags (Uint32)', 4,  0 ,'int', 0, 0, 0, 0, 18, 'NA');

-- Arrow1 Arrays

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (601, hex_to_int('7014'), 'Amps', 'Amps', 4,  4 ,'int', 0, 0, 0, 0, 31, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (602, hex_to_int('7010'), 'Volts', 'Volts', 4,  0 ,'int', 0, 0, 0, 0, 31, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (603, hex_to_int('7026'), 'Ambient Temp', 'Ambient Temp', 1,  6 ,'int', 0, 0, 0, 0, 32, 'Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (604, hex_to_int('7025'), 'Vout', 'Vout', 1,  5 ,'int', 0, 0, 0, 0, 32, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (605, hex_to_int('7024'), 'Vout', 'Vout', 1,  4 ,'int', 0, 0, 0, 0, 32, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (606, hex_to_int('7023'), 'Amps In', 'Amps In', 1,  3 ,'int', 0, 0, 0, 0, 32, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (607, hex_to_int('7022'), 'Amps In', 'Amps In', 1,  2 ,'int', 0, 0, 0, 0, 32, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (608, hex_to_int('7021'), 'Vin', 'Vin', 1,  1 ,'int', 0, 0, 0, 0, 32, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (609, hex_to_int('7020'), 'Status / Vin', 'Status / Vin', 1,  0 ,'int', 0, 0, 0, 0, 32, 'NA');

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (611, hex_to_int('7054'), 'Amps', 'Amps', 4,  4 ,'int', 0, 0, 0, 0, 33, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (612, hex_to_int('7050'), 'Volts', 'Volts', 4,  0 ,'int', 0, 0, 0, 0, 33, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (613, hex_to_int('7066'), 'Ambient Temp', 'Ambient Temp', 1,  6 ,'int', 0, 0, 0, 0, 34, 'Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (614, hex_to_int('7065'), 'Vout', 'Vout', 1,  5 ,'int', 0, 0, 0, 0, 34, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (615, hex_to_int('7064'), 'Vout', 'Vout', 1,  4 ,'int', 0, 0, 0, 0, 34, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (616, hex_to_int('7063'), 'Amps In', 'Amps In', 1,  3 ,'int', 0, 0, 0, 0, 34, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (617, hex_to_int('7062'), 'Amps In', 'Amps In', 1,  2 ,'int', 0, 0, 0, 0, 34, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (618, hex_to_int('7061'), 'Vin', 'Vin', 1,  1 ,'int', 0, 0, 0, 0, 34, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (619, hex_to_int('7060'), 'Status / Vin', 'Status / Vin', 1,  0 ,'int', 0, 0, 0, 0, 34, 'NA');

insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (621, hex_to_int('7094'), 'Amps', 'Amps', 4,  4 ,'int', 0, 0, 0, 0, 35, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (622, hex_to_int('7090'), 'Volts', 'Volts', 4,  0 ,'int', 0, 0, 0, 0, 35, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (623, hex_to_int('70A6'), 'Ambient Temp', 'Ambient Temp', 1,  6 ,'int', 0, 0, 0, 0, 36, 'Degrees Celsius');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (624, hex_to_int('70A5'), 'Vout', 'Vout', 1,  5 ,'int', 0, 0, 0, 0, 36, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (625, hex_to_int('70A4'), 'Vout', 'Vout', 1,  4 ,'int', 0, 0, 0, 0, 36, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (626, hex_to_int('70A3'), 'Amps In', 'Amps In', 1,  3 ,'int', 0, 0, 0, 0, 36, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (627, hex_to_int('70A2'), 'Amps In', 'Amps In', 1,  2 ,'int', 0, 0, 0, 0, 36, 'mA');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (628, hex_to_int('70A1'), 'Vin', 'Vin', 1,  1 ,'int', 0, 0, 0, 0, 36, 'mV');
insert into data_pnt(DATA_PNT_ID, DATA_PNT_CAN_ID, NAME, DESCR, DATA_LEN, CAN_DATA_OFFST, DATA_TYPE, LOW_ERR_THRHLD, LOW_WRNG_THRHLD, HIGH_WRNG_THRHLD, HIGH_ERR_THRHLD, MSRMNT_ID_FK, UNITS) values (629, hex_to_int('70A0'), 'Status / Vin', 'Status / Vin', 1,  0 ,'int', 0, 0, 0, 0, 36, 'NA');



-- 
-- insert into msrmnt(MSRMNT_NAME, MSRMNT_TYPE_ID, DATA_PNT_CAN_ID, DEV_TYPE_ID, DEV_ID) values ('Battery Pack Voltage', 1, 1, 1, 1);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (255, now(), hex_to_int('ff'), true, false, 1, decode('01', 'hex'), 1, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (241, now(), hex_to_int('f1'), true, false, 1, decode('64', 'hex'), 100, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (242, now(), hex_to_int('f2'), true, false, 1, decode('65', 'hex'), 101, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (243, now(), hex_to_int('f3'), true, false, 1, decode('78', 'hex'), 120, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now(), hex_to_int('f4'), true, false, 1, decode('5A', 'hex'), 90, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '5 seconds', hex_to_int('f4'), true, false, 1, decode('50', 'hex'), 80, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '10 seconds', hex_to_int('f4'), true, false, 1, decode('46', 'hex'), 70, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '15 seconds', hex_to_int('f4'), true, false, 1, decode('46', 'hex'), 70, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '20 seconds', hex_to_int('f4'), true, false, 1, decode('3c', 'hex'), 60, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() + interval '5 seconds', hex_to_int('f4'), true, false, 1, decode('64', 'hex'), 100, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '1000 seconds', hex_to_int('f4'), true, false, 1, decode('64', 'hex'), 100, 0);
-- insert into msrmnt_data(DATA_PNT_ID, DATA_PNT_CAN_ID, TSTAMP, RAW_DEV_ID, EXTD, RTR, DATA_LEN, RAW_DATA, DATA_SEG_1, DATA_SEG_2) values (244, now() - interval '10000 seconds', hex_to_int('f4'), true, false, 1, decode('64', 'hex'), 100, 0);
