CREATE DATABASE airline_db;
GO
USE airline_db;
GO



CREATE TABLE booking (
    id INT IDENTITY(1,1) PRIMARY KEY,
    passengerName VARCHAR(50),
    flightName VARCHAR(50),
    date VARCHAR(20),
    status VARCHAR(20)
);

SELECT * FROM booking;




CREATE TABLE admin (
    username VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO admin VALUES ('admin','1234');


CREATE TABLE flights (
    flightId INT IDENTITY(1,1) PRIMARY KEY,
    flightName VARCHAR(50),
    source VARCHAR(50),
    destination VARCHAR(50)
);

INSERT INTO flights VALUES 
('AirIndia','Chennai','Delhi'),
('Indigo','Chennai','Mumbai'),
('FlyScoot','Trichy','Singapore'),
('Singapore Airlines','Chennai','Singapore'),
('Fly Emirates','Chennai','Dubai'),
('Air Asia','Chennai','Malaysia'),
('Malido air','sinagpore','Trichy'),
('Indigo','Chennai','Mumbai'),
('FlyScoot','Chennai','Singapore');



select * from flights
