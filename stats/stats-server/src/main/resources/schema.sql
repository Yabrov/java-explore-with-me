DROP TABLE IF EXISTS stats_history CASCADE;
CREATE TABLE IF NOT EXISTS stats_history(
    id integer NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    app character varying NOT NULL,
    uri character varying NOT NULL,
    ip character varying NOT NULL,
    real_time timestamp without time zone NOT NULL,
    CONSTRAINT stats_history_pkey PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS stats_history_uri_idx ON stats_history (uri);
CREATE INDEX IF NOT EXISTS stats_history_real_time_idx ON stats_history (real_time);