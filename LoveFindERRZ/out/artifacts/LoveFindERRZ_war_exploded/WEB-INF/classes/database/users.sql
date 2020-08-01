Drop DATABASE IF EXISTS user_base;
CREATE DATABASE user_base;

USE user_base;

DROP TABLE IF EXISTS users;

CREATE TABLE users(
    user_id DECIMAL(65) not null,
    username CHAR(64) not null,
    password CHAR(255) not null,
    description VARCHAR(15000),
    imageCount INT DEFAULT 0,
    image1 CHAR(255),
    image2 CHAR(255),
    image3 CHAR(255),
    image4 CHAR(255),
    image5 CHAR(255),
    image6 CHAR(255)

);

CREATE TABLE matchingTable(
    chooser DECIMAL (65),
    chosen DECIMAL (65),
    status CHAR(20)
);