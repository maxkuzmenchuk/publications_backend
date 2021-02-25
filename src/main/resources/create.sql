-- Create database:
-- schema - publications
-- tables - user_account, authorities, orders, publications
-- DDL for creating must be in one row!!

DROP SCHEMA IF EXISTS publications CASCADE;
CREATE SCHEMA IF NOT EXISTS publications;

-- Table: user_account

DROP TABLE IF EXISTS publications.user_account;
CREATE TABLE IF NOT EXISTS publications.user_account(user_id serial NOT NULL PRIMARY KEY, username VARCHAR(16)  NOT NULL, password VARCHAR(255) NOT NULL, enabled BOOLEAN NOT NULL);

-- Table: authorities

DROP TABLE IF EXISTS publications.authorities;
CREATE TABLE IF NOT EXISTS publications.authorities(authority_id serial NOT NULL PRIMARY KEY, user_id INTEGER, authority VARCHAR NOT NULL);
ALTER TABLE publications.authorities ADD CONSTRAINT fk_authorities_users FOREIGN KEY(user_id) references publications.user_account(user_id);
CREATE UNIQUE index ix_auth_user_id ON publications.authorities (user_id, authority);

-- Table: publications

DROP TABLE  IF EXISTS publications.publications;
CREATE TABLE IF NOT EXISTS publications.publications(publication_id serial NOT NULL PRIMARY KEY, publ_name VARCHAR NOT NULL, price DOUBLE PRECISION, image_name VARCHAR(255), description VARCHAR(255), CONSTRAINT publications_price_check CHECK (price >= 0));

-- Table: orders

DROP TABLE  IF EXISTS publications.orders;
CREATE TABLE IF NOT EXISTS publications.orders(order_id serial NOT NULL PRIMARY KEY, amount INTEGER, date TIMESTAMP WITHOUT TIME ZONE, price DOUBLE PRECISION, status VARCHAR(255), o_publication_id INTEGER NOT NULL, o_user_id INTEGER NOT NULL);
ALTER TABLE publications.orders ADD CONSTRAINT fk_orders_publications FOREIGN KEY(o_publication_id) references publications.publications(publication_id);
ALTER TABLE publications.orders ADD CONSTRAINT fk_orders_user_account FOREIGN KEY(o_user_id) references publications.user_account(user_id);