
// FIRST OF ALL CREATE DATABASE IN MYSQL
CREATE DATABASE `rss-collection`

// THEN CREATE TABLE INSIDE
CREATE TABLE `email_rss` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `email` varchar(45) NOT NULL,
                             `rss` varchar(85) NOT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

// AND FINALLY CHANGE USERNAME AND PASSWORD IN application-wsb.yml ON YOURS
// My are:
// username: root
// password: 1234