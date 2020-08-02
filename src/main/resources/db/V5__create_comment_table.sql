create TABLE comment(
    id              BIGSERIAL PRIMARY KEY,

    commenterId     VARCHAR(50),
    title           VARCHAR(200),
    status          VARCHAR(20),
    publishedAt     TIMESTAMP,
    content         TEXT,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

create TABLE postComment(
    id            BIGSERIAL PRIMARY KEY,

    postId        BIGINT NOT NULL references post,
    commentId     BIGINT NOT NULL references comment,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

create index on postComment(postId);



