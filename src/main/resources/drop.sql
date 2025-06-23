
    set client_min_messages = WARNING;

    alter table if exists movie_finder.movie_buff_hobbies 
       drop constraint if exists FKj7wdgfh27njulqcag0eqwq374;

    alter table if exists movie_finder.movie_buff_movie_rating 
       drop constraint if exists FK5axeq8jjujsgtb2b8qa9fvwtw;

    alter table if exists movie_finder.movie_buff_movie_rating 
       drop constraint if exists FKsvgif3lsiyiupvc1bojf2rrk9;

    drop table if exists movie_finder.movie_buff_hobbies cascade;

    drop table if exists movie_finder.movie_buff cascade;

    drop table if exists movie_finder.movie_buff_movie_rating cascade;

    drop table if exists movie_finder.movie_rating cascade;

    drop schema movie_finder;
