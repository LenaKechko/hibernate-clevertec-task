-- Database: myHibernateDatabase

DROP TABLE IF EXISTS house;
DROP TABLE IF EXISTS person;

DROP DATABASE IF EXISTS myHibernateDatabase;

CREATE DATABASE myHibernateDatabase;

CREATE TABLE IF NOT EXISTS houses
(
    id serial PRIMARY KEY,
    uuid uuid NOT NULL UNIQUE,
    area double precision,
    country character varying(30),
    city character varying(30),
    street character varying(30),
    number integer,
    create_date timestamp(3) with time zone
);

CREATE TABLE IF NOT EXISTS persons
(
    id serial PRIMARY KEY,
    uuid uuid NOT NULL UNIQUE,
    name character varying(30) NOT NULL,
    surname character varying(30) NOT NULL,
    sex character varying(10) CHECK (sex = 'MALE' or sex = 'FEMALE'),
    passport_series character varying(5),
    passport_number character varying(10),
    create_date timestamp(3) with time zone,
    update_date timestamp(3) with time zone,
    id_live_house integer NOT NULL,
    FOREIGN KEY (id_live_house) REFERENCES houses(id),
    CONSTRAINT unique_passport UNIQUE(passport_series, passport_number)
);

CREATE TABLE IF NOT EXISTS owners
(
    id serial PRIMARY KEY,
    id_house integer,
    id_person integer,
    FOREIGN KEY (id_house) REFERENCES houses(id),
    FOREIGN KEY (id_person) REFERENCES persons(id)
);

INSERT INTO houses(uuid, area, country, city, street, number, create_date)
    VALUES (gen_random_uuid(), '40.1', 'Беларусь', 'Гомель', 'Советская', 3, '2023-01-19T14:00:00.130');
INSERT INTO houses(uuid, area, country, city, street, number, create_date)
    VALUES (gen_random_uuid(), '35.5', 'Беларусь', 'Минск', 'Автозаводская', 13, '2000-07-13T12:13:00.120');
INSERT INTO houses(uuid, area, country, city, street, number, create_date)
    VALUES (gen_random_uuid(), '48.4', 'Беларусь', 'Брест', 'Сябровская', 166, '2004-04-14T14:14:14.100');
INSERT INTO houses(uuid, area, country, city, street, number, create_date)
    VALUES (gen_random_uuid(), '78.6', 'Беларусь', 'Гомель', 'Кирова', 177, '1998-09-01T10:28:59.080');
INSERT INTO houses(uuid, area, country, city, street, number, create_date)
    VALUES (gen_random_uuid(), '65.3', 'Беларусь', 'Гомель', 'Головацкого', 57, '2016-05-27T15:02:07.005');

INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Антон', 'Иванов', 'MALE', 'HB', '1002030', '2001-12-01T10:10:10.100', '2001-12-01T10:10:10.100', 1);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Иван', 'Павлов', 'MALE', 'HB', '2003040', '2010-05-17T15:24:59.632', '2010-05-17T15:24:59.632', 1);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Александр', 'Шахов', 'MALE', 'HB', '1502030', '2017-09-11T11:11:11.111', '2017-09-11T11:11:11.111', 2);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Ольга', 'Легенькая', 'FEMALE', 'HB', '7894561', '2003-04-05T17:17:00.000', '2003-04-05T17:17:00.000', 2);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Елена', 'Самойлова', 'FEMALE', 'HB', '9876541', '1999-10-29T09:06:10.058', '1999-10-29T09:06:10.058', 3);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Захар', 'Семенов', 'MALE', 'HB', '3571590', '1998-11-20T11:20:35.561', '1998-11-20T11:20:35.561', 3);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Роман', 'Самец', 'MALE', 'HB', '8526540', '2000-07-03T15:53:23.125', '2000-07-03T15:53:23.125', 4);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Ирина', 'Симхович', 'FEMALE', 'HB', '3210456', '2002-06-30T15:26:37.456', '2002-06-30T15:26:37.456', 4);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Екатерина', 'Великая', 'FEMALE', 'HB', '6521987', '2011-10-21T23:25:27.290', '2011-10-21T23:25:27.290', 5);
INSERT INTO persons(uuid, name, surname, sex, passport_series, passport_number, create_date, update_date, id_live_house)
    values (gen_random_uuid(), 'Дмитрий', 'Качевой', 'MALE', 'HB', '2345678', '2015-02-13T16:31:15.536', '2015-02-13T16:31:15.536', 5);

INSERT INTO owners(id_house, id_person)
    values (1, 2);
INSERT INTO owners(id_house, id_person)
    values (1, 1);
INSERT INTO owners(id_house, id_person)
    values (1, 8);
INSERT INTO owners(id_house, id_person)
    values (2, 4);
INSERT INTO owners(id_house, id_person)
    values (3, 3);
INSERT INTO owners(id_house, id_person)
    values (4, 7);
INSERT INTO owners(id_house, id_person)
    values (5, 1);