
    create schema movie_finder;

    create table movie_finder.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table movie_finder.movie_buff (
        about varchar(255),
        country_code varchar(255),
        id varchar(255) not null,
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table movie_finder.movie_buff_movie_rating (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table movie_finder.movie_rating (
        rating integer not null,
        id varchar(255) not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists movie_finder.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references movie_finder.movie_buff;

    alter table if exists movie_finder.movie_buff_movie_rating 
       add constraint FK5axeq8jjujsgtb2b8qa9fvwtw 
       foreign key (movie_ratings_id) 
       references movie_finder.movie_rating;

    alter table if exists movie_finder.movie_buff_movie_rating 
       add constraint FKsvgif3lsiyiupvc1bojf2rrk9 
       foreign key (movie_buff_id) 
       references movie_finder.movie_buff;
