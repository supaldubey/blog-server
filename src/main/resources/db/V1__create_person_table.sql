create TABLE person(
    id bigserial PRIMARY KEY,
    firstName varchar(200) NOT NULL,
    lastName varchar(200),
    email varchar(200),
    username varchar(200),
    password varchar(200),
    phone varchar(20),
    countryCode varchar(10),
    createdAt timestamp NOT NULL,
    updatedAt timestamp NOT NULL
);

CREATE UNIQUE INDEX phone_unique_idx ON person (phone) WHERE (phone is NOT null);
CREATE UNIQUE INDEX username_unique_idx ON person (username) WHERE (username is NOT null);
CREATE UNIQUE INDEX email_unique_idx ON person (email) WHERE (email is NOT null);