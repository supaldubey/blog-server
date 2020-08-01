create TABLE person(
    id              BIGSERIAL PRIMARY KEY,

    firstName       VARCHAR(200) NOT NULL,
    lastName        VARCHAR(200),
    email           VARCHAR(200),
    username        VARCHAR(200),
    password        VARCHAR(200),
    status          VARCHAR(50),
    profileImage    VARCHAR(500),
    phone           VARCHAR(20),
    countryCode     VARCHAR(10),

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX phone_unique_idx ON person (phone) WHERE (phone is NOT null);
CREATE UNIQUE INDEX username_unique_idx ON person (username) WHERE (username is NOT null);
CREATE UNIQUE INDEX email_unique_idx ON person (email) WHERE (email is NOT null);