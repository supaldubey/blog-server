create TABLE post(
    id              BIGSERIAL PRIMARY KEY,

    authorId        BIGINT NOT NULL references Person,
    title           TEXT,
    metaTitle       TEXT,
    summary         VARCHAR(5000),
    slug            VARCHAR(500),
    postStatus      VARCHAR(20),
    status          VARCHAR(50),
    publishedAt     TIMESTAMP,
    content         TEXT,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

