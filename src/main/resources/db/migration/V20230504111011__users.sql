create table users
(
    id       SERIAL
        constraint users_pk
            primary key,
    name     varchar not null
        constraint users_pk2
            unique,
    password varchar,
    role     varchar
);