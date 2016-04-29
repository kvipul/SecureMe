create database project;

use project;

create table location(
	id varchar(10),
	latitude varchar(20),
	longitude varchar(20),
	time timestamp default current_timestamp
);
create table track_all_rec(
	mob1 varchar(10),
	mob2 varchar(10),
	latitude varchar(20),
	longitude varchar(20),
	time timestamp default current_timestamp
);
