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
    salt            VARCHAR(200),
    countryCode     VARCHAR(10),

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

CREATE UNIQUE INDEX ON person (phone) WHERE (phone is NOT null);
CREATE UNIQUE INDEX ON person (username) ;
CREATE UNIQUE INDEX on person (email) WHERE (email is NOT null);