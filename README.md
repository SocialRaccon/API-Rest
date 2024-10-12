# API-Rest
This is the API Rest for the Social Raccon App

## Setup
1. Clone the repository
2. Run mvn clean install
3. Run script.sql in your database

### Script.sql
```sql

-- Insert data into users table
USE raccoondb;
CREATE TABLE user
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(50) NOT NULL,
    lastName       VARCHAR(50) NOT NULL,
    secondLastName VARCHAR(50) NOT NULL,
    email          VARCHAR(50) NOT NULL,
    controlNumber  VARCHAR(50) NOT NULL,
    CONSTRAINT unique_email UNIQUE (email),
    CONSTRAINT unique_controlNumber UNIQUE (controlNumber)
);

CREATE TABLE post
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    dateCreated DATE         NOT NULL,
    description TEXT         NOT NULL,
    imageUrl    VARCHAR(255) NOT NULL,
    idUser      INT          NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (id)
);

CREATE TABLE comment
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    comment TEXT NOT NULL,
    date    DATE NOT NULL,
    idUser  INT NOT NULL,
    idPost  INT NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (id),
    FOREIGN KEY (idPost) REFERENCES post (id)
);
-- Insert data into users table
INSERT INTO user (name, lastName, secondLastName, email, controlNumber)
VALUES ('Alejandro', 'Tejeda', 'Moreno', 'alex2227@htomil.com', '21TE284'),
       ('Gerardo', 'García', 'García', 'gerd2@gmail.com', '21TE285'),
       ('José', 'García', 'García', 'josesito@gmail.com', '21TE286');

-- Insert data into posts table
INSERT INTO post (dateCreated, description, imageUrl, idUser)
VALUES ('2021-10-10', 'This is a post', 'https://www.google.com', 1),
       ('2021-10-10', 'This is a post', 'https://www.google.com', 2),
       ('2021-10-10', 'This is a post', 'https://www.google.com', 3);

-- Insert data into comments table
INSERT INTO comment (comment, date, idUser, idPost)
VALUES ('This is a comment', '2021-10-10', 1, 1),
       ('This is a comment', '2021-10-10', 2, 2),
       ('This is a comment', '2021-10-10', 3, 3);

SELECT *
FROM post;
SELECT *
FROM user;
SELECT *
FROM comment;
````