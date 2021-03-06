CREATE DATABASE sql9379175;

CREATE TABLE sql9379175.users
(
username CHAR(60) NOT NULL PRIMARY KEY,
password VARCHAR(36) NOT NULL
);

CREATE TABLE sql9379175.individuals
(
individualId INT(30) NOT NULL PRIMARY KEY AUTO_INCREMENT,
name char(36) NOT NULL,
role char(60) NOT NULL,
username CHAR(60)  NOT NULL,
FOREIGN KEY (username) REFERENCES sql9379175.users(username)
);