# API-Rest
This is the API Rest for the Social Raccon App, it implements a SQL database with the following tables, but it is not necessary to create the database, the application will create it automatically.

It doesn't use NoSQL databases because we don't need to store large amounts of data, and the data we need to store is relational.
Also we doesn't implement NoSQL databases because we analyze our use case and we don't need to store large amounts of data such user comments, posts, etc.

## Setup
1. Clone the repository
2. Run mvn clean install
3. Run script.sql in your database

### Script.sql
```sql
CREATE DATABASE raccoondb;

USE raccoondb;

CREATE TABLE career
(
    idCareer INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(65) NOT NULL,
    acronym  VARCHAR(10) NOT NULL,
    CONSTRAINT unique_acronym UNIQUE (acronym)
);

CREATE TABLE authentication
(
    idAuthentication INT AUTO_INCREMENT PRIMARY KEY,
    email            VARCHAR(65) NOT NULL,
    password         VARCHAR(65) NOT NULL,
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE user
(
    idUser           INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(65) NOT NULL,
    lastName         VARCHAR(65) NOT NULL,
    secondLastName   VARCHAR(65) NOT NULL,
    controlNumber    VARCHAR(8)  NOT NULL,
    idCareer         INT         NOT NULL,
    idAuthentication INT         NOT NULL,
    FOREIGN KEY (idCareer) REFERENCES career (idCareer),
    FOREIGN KEY (idAuthentication) REFERENCES authentication (idAuthentication),
    CONSTRAINT unique_controlNumber UNIQUE (controlNumber)
);

CREATE TABLE relationship
(
    idUser     INT NOT NULL,
    idFollower INT NOT NULL,
    PRIMARY KEY (idUser, idFollower),
    FOREIGN KEY (idUser) REFERENCES user (idUser),
    FOREIGN KEY (idFollower) REFERENCES user (idUser)
);

CREATE TABLE profile
(
    idProfile   INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(150) NOT NULL,
    idUser      INT          NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (idUser)
);

CREATE TABLE image_profile
(
    idImageProfile    INT AUTO_INCREMENT PRIMARY KEY,
    idProfile         INT          NOT NULL,
    imageUrl          VARCHAR(255) NOT NULL,
    imageThumbnailUrl VARCHAR(255) NOT NULL,
    FOREIGN KEY (idProfile) REFERENCES profile (idProfile)
);

CREATE TABLE post
(
    idPost      INT AUTO_INCREMENT PRIMARY KEY,
    dateCreated DATE NOT NULL,
    idUser      INT  NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (idUser)
);

CREATE TABLE image_post
(
    idImagePost       INT AUTO_INCREMENT PRIMARY KEY,
    imageUrl          VARCHAR(255) NOT NULL,
    imageThumbnailUrl VARCHAR(255) NOT NULL,
    idPost            INT          NOT NULL,
    FOREIGN KEY (idPost) REFERENCES post (idPost)
);

CREATE TABLE post_description
(
    idPostDescription INT AUTO_INCREMENT PRIMARY KEY,
    description       VARCHAR(150) NOT NULL,
    idPost            INT          NOT NULL,
    FOREIGN KEY (idPost) REFERENCES post (idPost)
);

CREATE TABLE comment
(
    idComment INT AUTO_INCREMENT PRIMARY KEY,
    comment   VARCHAR(150) NOT NULL,
    date      DATE         NOT NULL,
    idUser    INT          NOT NULL,
    idPost    INT          NOT NULL,
    FOREIGN KEY (idUser) REFERENCES user (idUser),
    FOREIGN KEY (idPost) REFERENCES post (idPost)
);

CREATE TABLE reaction_icon
(
    idReactionIcon   INT AUTO_INCREMENT PRIMARY KEY,
    iconUrl          VARCHAR(255) NOT NULL,
    iconThumbnailUrl VARCHAR(255) NOT NULL
);

CREATE TABLE reaction_type
(
    idReactionType INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(65) NOT NULL,
    idReactionIcon INT         NOT NULL,
    FOREIGN KEY (idReactionIcon) REFERENCES reaction_icon (idReactionIcon)
);

CREATE TABLE reaction
(
    idReactionType INT NOT NULL,
    idUser         INT NOT NULL,
    idPost         INT NOT NULL,
    createdDate    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (idUser, idPost, idReactionType),
    FOREIGN KEY (idUser) REFERENCES user (idUser),
    FOREIGN KEY (idPost) REFERENCES post (idPost)
);

INSERT INTO career (name, acronym)
VALUES ('Ingeniería en Sistemas Computacionales', 'ISC'),
       ('Ingeniería en Gestión Empresarial', 'IGE'),
       ('Ingeniería en Mecatrónica', 'IME');

INSERT INTO authentication (email, password)
VALUES ('juan@teziutlan.tecnm.mx', '12tT3456'),
       ('maria@teziutlan.tecnm.mx', '12fF3456'),
       ('pedro@teziutlan.tecnm.mx', '123Yy456');

INSERT INTO user (name, lastName, secondLastName, controlNumber, idCareer, idAuthentication)
VALUES ('Juan', 'Pérez', 'Gómez', '21TE0121', 1, 1),
       ('María', 'González', 'Hernández', '21TE0122', 2, 2),
       ('Pedro', 'Martínez', 'López', '21TE0123', 3, 3);

INSERT INTO profile (description, idUser)
VALUES ('Estudiante de ISC', 1),
       ('Estudiante de IGE', 2),
       ('Estudiante de IME', 3);

INSERT INTO relationship (idUser, idFollower)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 3),
       (3, 1),
       (3, 2);

INSERT INTO image_profile (idProfile, imageUrl, imageThumbnailUrl)
VALUES (1, 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1'),
       (2, 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1'),
       (3, 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1');

INSERT INTO post (dateCreated, idUser)
VALUES ('2021-10-01', 1),
       ('2021-10-02', 2),
       ('2021-10-03', 3);

INSERT INTO image_post (imageUrl, imageThumbnailUrl, idPost)
VALUES ('https://www.example.com/image_post1.jpg', 'https://www.example.com/image_post1_thumbnail.jpg', 1),
       ('https://www.example.com/image_post2.jpg', 'https://www.example.com/image_post2_thumbnail.jpg', 2),
       ('https://www.example.com/image_post3.jpg', 'https://www.example.com/image_post3_thumbnail.jpg', 3);

INSERT INTO post_description (description, idPost)
VALUES ('Evento colecta', 1),
       ('EXAMEN FINAL', 2),
       ('Mapachitos TEC', 3);

INSERT INTO comment (comment, date, idUser, idPost)
VALUES ('Hola como esta eso?', '2021-10-01', 2, 1),
       ('Siempre lo mismo, otra vez a repite :(', '2021-10-02', 3, 2),
       ('Vivan los mapaches!!', '2021-10-03', 1, 3);

INSERT INTO reaction_icon (iconUrl, iconThumbnailUrl)
VALUES ('https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaLike.png?alt=media&token=4ead8dba-4c77-417e-8c7b-00e3ec64b9c3', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaLike.png?alt=media&token=4ead8dba-4c77-417e-8c7b-00e3ec64b9c3'),
       ('https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaLove.png?alt=media&token=10dc5d23-5a60-40c5-944c-12dfb779558c', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaLove.png?alt=media&token=10dc5d23-5a60-40c5-944c-12dfb779558c'),
       ('https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/raccoon_love.png?alt=media&token=8a29e983-e4e8-4502-8b05-c17accbac07e', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/raccoon_love.png?alt=media&token=8a29e983-e4e8-4502-8b05-c17accbac07e'),
       ('https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaSad.png?alt=media&token=76eba7e7-01f0-47e9-956d-1e7814655dc7', 'https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/MeEnmapaSad.png?alt=media&token=76eba7e7-01f0-47e9-956d-1e7814655dc7');

INSERT INTO reaction_type (name, idReactionIcon)
VALUES ('MeEnmapaLike', 1),
       ('MeEnmapaLove', 2),
       ('MeEnMapacha', 3),
       ('MeEnmapaSad', 4);

INSERT INTO reaction (idReactionType, idUser, idPost, createdDate)
VALUES (1, 2, 1, '2021-10-01 12:00:00'),
       (2, 3, 2, '2021-10-02 12:00:00'),
       (3, 1, 3, '2021-10-03 12:00:00'),
       (4, 2, 2, '2021-10-04 12:00:00');
````