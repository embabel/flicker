-- init.sql
DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'flicker') THEN
            CREATE ROLE flicker WITH LOGIN PASSWORD 'look_to_the_stars';
        END IF;
    END
$$;

GRANT ALL PRIVILEGES ON DATABASE "flicker" TO flicker;
ALTER USER flicker CREATEDB;

CREATE SCHEMA IF NOT EXISTS flicker AUTHORIZATION flicker;
