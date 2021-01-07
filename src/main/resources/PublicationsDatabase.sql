CREATE DATABASE publications
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE SCHEMA publications;

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence
    OWNER TO postgres;

CREATE SEQUENCE publications.user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE publications.user_user_id_seq
    OWNER TO postgres;


create table publications.user_account
(
    id       serial       not null
        constraint user_account_pk
            primary key,
    username varchar(16)  not null,
    password varchar(255) not null
);

alter table publications.user_account
    owner to postgres;

create unique index user_account_id_uindex
    on publications.user_account (id);

create table publications.user_role
(
    id      serial  not null
        constraint user_role_pk
            primary key,
    user_id integer not null
        constraint user_role_user_account_id_fk
            references publications.user_account,
    role    varchar
);

alter table publications.user_role
    owner to postgres;

create unique index user_role_id_uindex
    on publications.user_role (id);

