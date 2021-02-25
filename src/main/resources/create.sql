-- Create database:
-- schema - publications
-- tables - user_account, user_role, orders, order_details, publications
-- DDL for creating must be in one row!!

-- DROP SCHEMA IF EXISTS publications CASCADE;
CREATE SCHEMA IF NOT EXISTS publications;

-- Table: user_account

-- DROP TABLE IF EXISTS publications.user_account;
CREATE TABLE IF NOT EXISTS publications.user_account (id serial NOT NULL PRIMARY KEY, username VARCHAR(16)  NOT NULL, password VARCHAR(255) NOT NULL);

-- Table: user_role

-- DROP TABLE IF EXISTS publications.user_role;
CREATE TABLE IF NOT EXISTS publications.user_role (id serial NOT NULL PRIMARY KEY, user_id INTEGER NOT NULL CONSTRAINT user_role_user_account_id_fk REFERENCES publications.user_account, role VARCHAR(10));

-- Table: orders

-- DROP TABLE  IF EXISTS publications.orders;
CREATE TABLE IF NOT EXISTS publications.orders (id serial NOT NULL PRIMARY KEY, amount INTEGER, date TIMESTAMP WITHOUT TIME ZONE, price DOUBLE PRECISION, status VARCHAR(255), user_id  INTEGER, username VARCHAR(255));

-- Table: order_details

-- DROP TABLE IF EXISTS publications.order_details;
CREATE TABLE IF NOT EXISTS publications.order_details (order_id INTEGER NOT NULL, publications_id INTEGER);

-- Table: publications

-- DROP TABLE  IF EXISTS publications.publications;
CREATE TABLE IF NOT EXISTS publications.publications (id serial NOT NULL PRIMARY KEY, description VARCHAR(255), image_name VARCHAR(255), name VARCHAR(255), price INTEGER, CONSTRAINT publications_price_check CHECK (price >= 0));
