DROP TABLE IF EXISTS compilation_events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS locations CASCADE;
DROP TABLE IF EXISTS allowed_locations CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR                                 NOT NULL,
    email VARCHAR(512)                            NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR                                 NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lon REAL                                    NOT NULL,
    lat REAL                                    NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id),
    CONSTRAINT uq_coordinates UNIQUE (lon, lat)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    created_on         timestamp                               NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         timestamp                               NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_id        BIGINT                                  NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  INTEGER                                 NOT NULL,
    published_on       timestamp,
    request_moderation BOOLEAN                                 NOT NULL,
    state              VARCHAR(10)                             NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT events_locations_fkey FOREIGN KEY (location_id)
        REFERENCES locations (id),
    CONSTRAINT events_users_fkey FOREIGN KEY (initiator_id)
        REFERENCES users (id),
    CONSTRAINT events_categories_fkey FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

CREATE INDEX IF NOT EXISTS initiator_id_idx ON events (initiator_id);
CREATE INDEX IF NOT EXISTS event_date_idx ON events (event_date);
CREATE INDEX IF NOT EXISTS state_idx ON events (state);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      timestamp                               NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR(10)                             NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT uq_request UNIQUE (event_id, requester_id),
    CONSTRAINT requests_events_fkey FOREIGN KEY (event_id)
        REFERENCES events (id),
    CONSTRAINT requests_users_fkey FOREIGN KEY (requester_id)
        REFERENCES users (id)
);

CREATE INDEX IF NOT EXISTS event_id_idx ON requests (event_id);
CREATE INDEX IF NOT EXISTS user_id_idx ON requests (requester_id);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR                                 NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id),
    CONSTRAINT uq_compilation_name UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id       BIGINT                                  NOT NULL,
    compilation_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_compilation_events PRIMARY KEY (id),
    CONSTRAINT compilations_events_fkey FOREIGN KEY (event_id)
        REFERENCES events (id),
    CONSTRAINT events_compilations_fkey FOREIGN KEY (compilation_id)
        REFERENCES compilations (id)
);

CREATE TABLE IF NOT EXISTS allowed_locations
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lon  REAL                                    NOT NULL,
    lat  REAL                                    NOT NULL,
    rad  REAL                                    NOT NULL,
    type VARCHAR(20)                             NOT NULL,
    name VARCHAR                                 NOT NULL,
    CONSTRAINT pk_allowed_locations PRIMARY KEY (id),
    CONSTRAINT uq_coordinates_allowed UNIQUE (lon, lat)
);

CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float)
    RETURNS float
AS
'
    declare
        dist      float = 0;
        rad_lat1  float;
        rad_lat2  float;
        theta     float;
        rad_theta float;
    BEGIN
        IF lat1 = lat2 AND lon1 = lon2
        THEN
            RETURN dist;
        ELSE
            -- переводим градусы широты в радианы
            rad_lat1 = pi() * lat1 / 180;
            -- переводим градусы долготы в радианы
            rad_lat2 = pi() * lat2 / 180;
            -- находим разность долгот
            theta = lon1 - lon2;
            -- переводим градусы в радианы
            rad_theta = pi() * theta / 180;
            -- находим длину ортодромии
            dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

            IF dist > 1
            THEN
                dist = 1;
            END IF;

            dist = acos(dist);
            -- переводим радианы в градусы
            dist = dist * 180 / pi();
            -- переводим градусы в километры
            dist = dist * 60 * 1.8524;

            RETURN dist;
        END IF;
    END;
'
    LANGUAGE PLPGSQL;

