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

-- Table: publications.orders

-- DROP TABLE publications.orders;

CREATE TABLE publications.orders
(
    id integer NOT NULL,
    amount integer,
    date timestamp without time zone,
    price double precision,
    status character varying(255) COLLATE pg_catalog."default",
    user_id integer,
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT orders_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE publications.orders
    OWNER to postgres;

-- Table: publications.order_details

-- DROP TABLE publications.order_details;

CREATE TABLE publications.order_details
(
    order_id integer NOT NULL,
    publications_id integer,
    CONSTRAINT fkjyu2qbqt8gnvno9oe9j2s2ldk FOREIGN KEY (order_id)
        REFERENCES publications.orders (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE publications.order_details
    OWNER to postgres;

-- Table: publications.publications

-- DROP TABLE publications.publications;

CREATE TABLE publications.publications
(
    id integer NOT NULL,
    description character varying(255) COLLATE pg_catalog."default",
    image_name character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price integer,
    CONSTRAINT publications_pkey PRIMARY KEY (id),
    CONSTRAINT publications_price_check CHECK (price >= 0)
)

    TABLESPACE pg_default;

ALTER TABLE publications.publications
    OWNER to postgres;

