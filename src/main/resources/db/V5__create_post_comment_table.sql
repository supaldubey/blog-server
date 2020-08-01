create TABLE post_comment(
    id            BIGSERIAL PRIMARY KEY,

    postId        BIGINT NOT NULL references Post,
    commentId     BIGINT NOT NULL references Comment,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

