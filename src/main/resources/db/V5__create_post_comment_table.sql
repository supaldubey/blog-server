create TABLE post_comment(
    id            BIGSERIAL PRIMARY KEY,

    postId        BIGINT NOT NULL references post,
    commentId     BIGINT NOT NULL references comment,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL,

    unique idx_post_comment (postId, commentId)
);

