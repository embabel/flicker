-- init.sql
DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolename = 'embabel') THEN
            CREATE ROLE embabel WITH LOGIN PASSWORD 'look_to_the_stars';
        END IF;
    END
$$;

GRANT ALL PRIVILEGES ON DATABASE "movie-finder" TO embabel;
ALTER USER embabel CREATEDB;