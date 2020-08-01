create TABLE post(
    id              BIGSERIAL PRIMARY KEY,

    authorId        BIGINT NOT NULL references Person,
    title           VARCHAR(200),
    metaTitle       VARCHAR(200),
    summary         VARCHAR(500),
    slug            VARCHAR(500),
    postStatus      VARCHAR(20),
    status          VARCHAR(50),
    publishedAt     TIMESTAMP,
    content         TEXT,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

