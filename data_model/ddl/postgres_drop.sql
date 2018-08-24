 ALTER TABLE public.data_pnt DROP CONSTRAINT msrmnt_data_pnt;
 ALTER TABLE public.msrmnt DROP CONSTRAINT dev_msrmnt;
 ALTER TABLE public.msrmnt DROP CONSTRAINT msrmnt_dev_type;
 ALTER TABLE public.lng_term_trend_data DROP CONSTRAINT lng_term_trend_data_state_check;
 ALTER TABLE public.med_term_trend_data DROP CONSTRAINT med_term_trend_data_state_check;
 ALTER TABLE public.msrmnt_data DROP CONSTRAINT msrmnt_data_state_check;
 ALTER TABLE public.sht_term_trend_data DROP CONSTRAINT sht_term_trend_data_state_check;
 ALTER TABLE public.data_pnt DROP CONSTRAINT data_pnt_pk;
 ALTER TABLE public.dev DROP CONSTRAINT dev_pk;
 ALTER TABLE public.dev_type DROP CONSTRAINT dev_type_pk;
 ALTER TABLE public.lng_term_trend_data DROP CONSTRAINT lng_term_trend_data_pk;
 ALTER TABLE public.med_term_trend_data DROP CONSTRAINT med_term_trend_data_pk;
 ALTER TABLE public.msrmnt DROP CONSTRAINT msrmnt_pk;
 ALTER TABLE public.msrmnt_data DROP CONSTRAINT msrmnt_data_pk;
 ALTER TABLE public.sht_term_trend_data DROP CONSTRAINT sht_term_trend_data_pk;
 ALTER TABLE public.dev_type DROP CONSTRAINT dev_type_uk;


DROP VIEW ALERTS;
DROP TABLE DATA_PNT CASCADE;
DROP TABLE DEV CASCADE;
DROP TABLE DEV_TYPE CASCADE;
DROP TABLE LNG_TERM_TREND_DATA CASCADE;
DROP TABLE MED_TERM_TREND_DATA CASCADE;
DROP TABLE MSRMNT CASCADE;
DROP TABLE MSRMNT_TYPE;
DROP TABLE MSRMNT_DATA CASCADE;
DROP TABLE SHT_TERM_TREND_DATA CASCADE;
DROP TABLE AVG_POWER CASCADE;