
    create table embabel.movie_buff_hobbies (
        hobby varchar(255),
        movie_buff_id varchar(255) not null
    );

    create table embabel.movie_buff (
        about varchar(255),
        country_code varchar(255),
        id varchar(255) not null,
        name varchar(255),
        streaming_services varchar(255) array,
        primary key (id)
    );

    create table embabel.movie_buff_movie_ratings (
        movie_buff_id varchar(255) not null,
        movie_ratings_id varchar(255) not null unique
    );

    create table embabel.movie_guide (
        id varchar(255) not null,
        name varchar(255),
        objective varchar(255),
        persona varchar(255),
        role varchar(255),
        voice varchar(255),
        primary key (id)
    );

    create table embabel.movie_rating (
        rating integer not null,
        id varchar(255) not null,
        title varchar(255),
        primary key (id)
    );

    alter table if exists embabel.movie_buff_hobbies 
       add constraint FKj7wdgfh27njulqcag0eqwq374 
       foreign key (movie_buff_id) 
       references embabel.movie_buff;

    alter table if exists embabel.movie_buff_movie_ratings 
       add constraint FK8pwc1lpa173nok67hmybt3du 
       foreign key (movie_ratings_id) 
       references embabel.movie_rating;

    alter table if exists embabel.movie_buff_movie_ratings 
       add constraint FKo77n808aqc7xb2nu0ij97c0xt 
       foreign key (movie_buff_id) 
       references embabel.movie_buff;
create table flicker.movie_buff_hobbies (hobby varchar(255), movie_buff_id varchar(255) not null);
create table flicker.movie_buff (about varchar(255), country_code varchar(255), id varchar(255) not null, name varchar(255), streaming_services varchar(255) array, primary key (id));
create table flicker.movie_buff_movie_ratings (movie_buff_id varchar(255) not null, movie_ratings_id varchar(255) not null unique);
create table flicker.movie_guide (id varchar(255) not null, name varchar(255), objective varchar(255), persona varchar(255), role varchar(255), voice varchar(255), primary key (id));
create table flicker.movie_rating (rating integer not null, id varchar(255) not null, title varchar(255), primary key (id));
alter table if exists flicker.movie_buff_hobbies add constraint FKj7wdgfh27njulqcag0eqwq374 foreign key (movie_buff_id) references flicker.movie_buff;
alter table if exists flicker.movie_buff_movie_ratings add constraint FK8pwc1lpa173nok67hmybt3du foreign key (movie_ratings_id) references flicker.movie_rating;
alter table if exists flicker.movie_buff_movie_ratings add constraint FKo77n808aqc7xb2nu0ij97c0xt foreign key (movie_buff_id) references flicker.movie_buff;
create table flicker.movie_buff_hobbies (hobby varchar(255), movie_buff_id varchar(255) not null);
create table flicker.movie_buff (about varchar(255), country_code varchar(255), id varchar(255) not null, name varchar(255), streaming_services varchar(255) array, primary key (id));
create table flicker.movie_buff_movie_ratings (movie_buff_id varchar(255) not null, movie_ratings_id varchar(255) not null unique);
create table flicker.movie_guide (id varchar(255) not null, name varchar(255), objective varchar(255), persona varchar(255), role varchar(255), voice varchar(255), primary key (id));
create table flicker.movie_rating (rating integer not null, id varchar(255) not null, title varchar(255), primary key (id));
alter table if exists flicker.movie_buff_hobbies add constraint FKj7wdgfh27njulqcag0eqwq374 foreign key (movie_buff_id) references flicker.movie_buff;
alter table if exists flicker.movie_buff_movie_ratings add constraint FK8pwc1lpa173nok67hmybt3du foreign key (movie_ratings_id) references flicker.movie_rating;
alter table if exists flicker.movie_buff_movie_ratings add constraint FKo77n808aqc7xb2nu0ij97c0xt foreign key (movie_buff_id) references flicker.movie_buff;
create table flicker.movie_buff_hobbies (hobby varchar(255), movie_buff_id varchar(255) not null);
create table flicker.movie_buff (about varchar(255), country_code varchar(255), id varchar(255) not null, name varchar(255), streaming_services varchar(255) array, primary key (id));
create table flicker.movie_buff_movie_ratings (movie_buff_id varchar(255) not null, movie_ratings_id varchar(255) not null unique);
create table flicker.movie_guide (id varchar(255) not null, name varchar(255), objective varchar(255), persona varchar(255), role varchar(255), voice varchar(255), primary key (id));
create table flicker.movie_rating (rating integer not null, id varchar(255) not null, title varchar(255), primary key (id));
alter table if exists flicker.movie_buff_hobbies add constraint FKj7wdgfh27njulqcag0eqwq374 foreign key (movie_buff_id) references flicker.movie_buff;
alter table if exists flicker.movie_buff_movie_ratings add constraint FK8pwc1lpa173nok67hmybt3du foreign key (movie_ratings_id) references flicker.movie_rating;
alter table if exists flicker.movie_buff_movie_ratings add constraint FKo77n808aqc7xb2nu0ij97c0xt foreign key (movie_buff_id) references flicker.movie_buff;
