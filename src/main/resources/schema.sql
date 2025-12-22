
    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        id varchar(255) not null,
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        id varchar(255) not null,
        name varchar(255),
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (id)
    );

    create table flicker.movie_rating (
        rating integer not null,
        id varchar(255) not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FKo77n808aqc7xb2nu0ij97c0xt 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        id varchar(255) not null,
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        id varchar(255) not null,
        name varchar(255),
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (id)
    );

    create table flicker.movie_rating (
        rating integer not null,
        id varchar(255) not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FKo77n808aqc7xb2nu0ij97c0xt 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    create table flicker.movie_buff_hobbies (
        movie_buff_id varchar(255) not null,
        hobby varchar(255)
    );

    create table flicker.movie_buff (
        id varchar(255) not null,
        about varchar(255),
        country_code varchar(255),
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null
    );

    create table flicker.movie_guide (
        id varchar(255) not null,
        name varchar(255),
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (id)
    );

    create table flicker.movie_rating (
        id varchar(255) not null,
        rating integer not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists UKam0n9wxjv8uakke51xu991oo6;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint UKam0n9wxjv8uakke51xu991oo6 unique (movie_ratings_id);

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FKo77n808aqc7xb2nu0ij97c0xt 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    create table flicker.movie_buff_hobbies (
        movie_buff_id varchar(255) not null,
        hobby varchar(255)
    );

    create table flicker.movie_buff (
        id varchar(255) not null,
        about varchar(255),
        country_code varchar(255),
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null
    );

    create table flicker.movie_guide (
        id varchar(255) not null,
        name varchar(255),
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (id)
    );

    create table flicker.movie_rating (
        id varchar(255) not null,
        rating integer not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists UKam0n9wxjv8uakke51xu991oo6;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint UKam0n9wxjv8uakke51xu991oo6 unique (movie_ratings_id);

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FKo77n808aqc7xb2nu0ij97c0xt 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;

    create schema flicker;

    create table flicker.movie (
        id varchar(255) not null,
        imdb_id varchar(255),
        title varchar(255),
        primary key (id)
    );

    create table flicker.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table flicker.movie_buff_streaming_services (
        movie_buff_id varchar(255) not null,
        streaming_service_id varchar(255) not null
    );

    create table flicker.movie_buff (
        about varchar(255),
        country_code varchar(255),
        display_name varchar(255),
        email varchar(255) not null,
        id varchar(255),
        movie_dislikes varchar(255),
        movie_likes varchar(255),
        username varchar(255),
        primary key (email)
    );

    create table flicker.movie_buff_movie_ratings (
        movie_buff_email varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table flicker.movie_guide (
        name varchar(255) not null,
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (name)
    );

    create table flicker.movie_rating (
        rating integer not null,
        timestamp timestamp(6) with time zone,
        id varchar(255) not null,
        movie_id varchar(255),
        primary key (id)
    );

    create table flicker.streaming_service (
        id varchar(255) not null,
        name varchar(255),
        url varchar(255),
        primary key (id)
    );

    alter table if exists flicker.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKi8yk0rsc4e3iyl9o9fwotucbh 
       foreign key (streaming_service_id) 
       references flicker.streaming_service;

    alter table if exists flicker.movie_buff_streaming_services 
       add constraint FKhc8exgjijofgmsirh4kjeh9ct 
       foreign key (movie_buff_id) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references flicker.movie_rating;

    alter table if exists flicker.movie_buff_movie_ratings 
       add constraint FK6ykscdllm2l5pp8gxso6ff4jl 
       foreign key (movie_buff_email) 
       references flicker.movie_buff;

    alter table if exists flicker.movie_rating 
       add constraint FKehmv7qvwmxkcvpwa9wwebwer6 
       foreign key (movie_id) 
       references flicker.movie;
