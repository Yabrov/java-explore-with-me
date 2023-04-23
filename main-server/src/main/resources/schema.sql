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
    CONSTRAINT locations_fkey FOREIGN KEY (location_id)
        REFERENCES locations (id),
    CONSTRAINT users_fkey FOREIGN KEY (initiator_id)
        REFERENCES users (id),
    CONSTRAINT categories_fkey FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description  TEXT                                    NOT NULL,
    created      timestamp                               NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR                                 NOT NULL,
    CONSTRAINT pk_requests PRIMARY KEY (id),
    CONSTRAINT events_fkey FOREIGN KEY (event_id)
        REFERENCES events (id),
    CONSTRAINT users_fkey FOREIGN KEY (requester_id)
        REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR                                 NOT NULL,
    CONSTRAINT pk_compilations PRIMARY KEY (id)
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

