
CREATE TABLE IF NOT EXISTS USER_INFO (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email varchar(50),
    password varchar(500),
    first_name varchar(100),
    last_name varchar(100)
);
