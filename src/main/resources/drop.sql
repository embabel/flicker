
    set client_min_messages = WARNING;

    alter table if exists flicker.movie_buff_hobbies 
       drop constraint if exists FKj7wdgfh27njulqcag0eqwq374;

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists FK8pwc1lpa173nok67hmybt3du;

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists FKo77n808aqc7xb2nu0ij97c0xt;

    drop table if exists flicker.movie_buff_hobbies cascade;

    drop table if exists flicker.movie_buff cascade;

    drop table if exists flicker.movie_buff_movie_ratings cascade;

    drop table if exists flicker.movie_guide cascade;

    drop table if exists flicker.movie_rating cascade;

    set client_min_messages = WARNING;

    alter table if exists flicker.movie_buff_hobbies 
       drop constraint if exists FKj7wdgfh27njulqcag0eqwq374;

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists FK8pwc1lpa173nok67hmybt3du;

    alter table if exists flicker.movie_buff_movie_ratings 
       drop constraint if exists FKo77n808aqc7xb2nu0ij97c0xt;

    drop table if exists flicker.movie_buff_hobbies cascade;

    drop table if exists flicker.movie_buff cascade;

    drop table if exists flicker.movie_buff_movie_ratings cascade;

    drop table if exists flicker.movie_guide cascade;

    drop table if exists flicker.movie_rating cascade;
