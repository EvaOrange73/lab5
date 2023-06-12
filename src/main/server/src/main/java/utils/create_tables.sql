create table users
(
    id       serial primary key,
    username text unique not null,
    password text
);
create table albums
(
    id     serial primary key,
    name   text   not null,
    tracks integer check ( tracks > 0 ),
    length bigint not null check ( length > 0 ),
    sales  bigint not null check ( sales > 0 )
);
create table coordinates
(
    id serial primary key,
    x  integer,
    y  float check ( y > -473 )
);
create table music_genres
(
    id   serial primary key,
    name text
);
insert into music_genres (name)
values ('HIP_HOP'),
       ('PSYCHEDELIC_CLOUD_RAP'),
       ('BLUES');

create table music_bands
(
    id                     serial primary key,
    creator_id             integer references users (id),
    name                   text    not null,
    coordinates            integer not null references coordinates (id),
    creation_date          date    not null default current_date,
    number_of_participants bigint check ( number_of_participants > 0 ),
    singles_count          bigint  not null check ( singles_count > 0 ),
    description            text,
    genre                  integer not null references music_genres (id),
    best_album             integer references albums (id)
)
