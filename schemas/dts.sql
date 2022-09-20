CREATE DATABASE dts;

USE dts;

CREATE TABLE accounts (
    username VARCHAR(20),
    password VARCHAR(20),
    email VARCHAR(320),
    PRIMARY KEY(username)
);

CREATE TABLE bugs (
    id INT UNSIGNED AUTO_INCREMENT,
    status BOOL,
    assignee VARCHAR(20),
    summary TINYTEXT,
    description TEXT,
    priority INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (assignee) REFERENCES accounts(username)
);

INSERT INTO accounts (username, password, email) VALUES ('admin', '', 'admin@example.com');
