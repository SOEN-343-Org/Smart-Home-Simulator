CREATE DATABASE homeDatabase;

CREATE TABLE homeDatabase.users
(
username CHAR(60) NOT NULL PRIMARY KEY,
password VARCHAR(36) NOT NULL
);

CREATE TABLE homeDatabase.individuals
(
individualId INT(30) NOT NULL PRIMARY KEY,
name char(36) NOT NULL,
role char(60) NOT NULL,
username CHAR(60)  NOT NULL,
FOREIGN KEY (username) REFERENCES homeDatabase.users(username)
);