drop table if exists USER_INFO;

create table USER_INFO(
	id int GENERATED ALWAYS AS IDENTITY primary key,
	name varchar(50),
	email varchar(50),
	password varchar(500),
	first_name varchar(100),
	last_name varchar(100)
);