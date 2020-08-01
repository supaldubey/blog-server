create TABLE comment(
    id              BIGSERIAL PRIMARY KEY,

    commenterId VARCHAR(50),
    title           VARCHAR(200),
    status          VARCHAR(20),
    publishedAt     TIMESTAMP,
    content         TEXT,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

