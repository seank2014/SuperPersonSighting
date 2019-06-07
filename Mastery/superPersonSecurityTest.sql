DROP DATABASE IF EXISTS superPersonSecurityTest;

CREATE DATABASE superPersonSecurityTest;

USE superPersonSecurityTest;

CREATE TABLE SuperPower(
SuperPowerId INT PRIMARY KEY AUTO_INCREMENT,
`Name` VARCHAR(45) NOT NULL
);

CREATE TABLE SuperPerson(
SuperPersonId INT PRIMARY KEY AUTO_INCREMENT,
SuperPowerId INT NOT NULL,
`Name` VARCHAR(45) UNIQUE NOT NULL,
`Description` TEXT NOT NULL,
IsVillian BOOLEAN NOT NULL,
FOREIGN KEY fk_SuperPerson_SuperPower(SuperPowerId) REFERENCES SuperPower(SuperPowerId)
);

CREATE TABLE Location(
LocationId INT PRIMARY KEY AUTO_INCREMENT,
`Description` TEXT NOT NULL,
Longitude DOUBLE NOT NULL,
Latitude DOUBLE NOT NULL,
Street VARCHAR(45) NOT NULL,
`City` VARCHAR(45) NOT NULL,
ZipCode INT NOT NULL,
`State` VARCHAR(45) NOT NULL
);

CREATE TABLE Sighting(
SightingId INT PRIMARY KEY AUTO_INCREMENT,
`Date` DATE NULL,
`Time` DATETIME NULL,
LocationId INT NOT NULL,
FOREIGN KEY fk_Sighting_Location(locationId) REFERENCES Location(locationId)
);

CREATE TABLE SuperPersonSighting(
SightingId INT NOT NULL,
SuperPersonId INT NOT NULL,
PRIMARY KEY pk_SuperPersonSighting(SightingId, SuperPersonId),
FOREIGN KEY fk_SuperPersonSighting_Sighting(SightingId) REFERENCES
Sighting(SightingId),
FOREIGN KEY fk_SuperPersonSighting_SuperPerson(SuperPersonId) REFERENCES
SuperPerson(SuperPersonId)
);

CREATE TABLE Org(
OrgId INT PRIMARY KEY AUTO_INCREMENT,
`Name` VARCHAR(45) UNIQUE NOT NULL,
`Description` TEXT NOT NULL,
ContactInfo VARCHAR(45) NOT NULL,
NumberOfMembers INT NOT NULL,
LocationId INT NOT NULL,
FOREIGN KEY fk_Org_Location(LocationId) REFERENCES Location(LocationId)
);

CREATE TABLE SuperPersonOrg(
OrgId INT NOT NULL,
SuperPersonId INT NOT NULL,
PRIMARY KEY SuperPersonOrg(OrgId, SuperPersonId),
FOREIGN KEY fk_SuperPersonOrg_Org(OrgId) REFERENCES Org(OrgId) ,
FOREIGN KEY fk_SuperPersonOrg_SuperPerson(SuperPersonId) REFERENCES SuperPerson(SuperPersonId)
);


ALTER TABLE Org
DROP COLUMN NumberOfMembers;

ALTER TABLE SuperPower
ADD COLUMN (
`Description` TEXT NOT NULL);

ALTER TABLE Location
ADD COLUMN (
`Name` VARCHAR(45) NOT NULL);

create table `user`(
`id` int primary key auto_increment,
`username` varchar(30) not null unique,
`password` varchar(100) not null,
`enabled` boolean not null);

create table `role`(
`id` int primary key auto_increment,
`role` varchar(30) not null
);

create table `user_role`(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`),
foreign key (`user_id`) references `user`(`id`),
foreign key (`role_id`) references `role`(`id`));

insert into `user`(`id`,`username`,`password`,`enabled`)
    values(1,"admin", "password", true),
        (2,"sidekick","password",true);

insert into `role`(`id`,`role`)
    values(1,"ROLE_ADMIN"), (2,"ROLE_SIDEKICK");
    
insert into `user_role`(`user_id`,`role_id`)
    values(1,1),(1,2),(2,2);

