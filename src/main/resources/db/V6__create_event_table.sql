create TABLE event(
    id              BIGSERIAL PRIMARY KEY,

    contentId       BIGINT NOT NULL,
    eventType       VARCHAR(25) NOT NULL,
    counterValue    BIGINT NOT NULL DEFAULT 0,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);

create index on event(contentId);

create TABLE postAnalytics(
    id              BIGSERIAL PRIMARY KEY,

    postId          BIGINT NOT NULL references post,
    likes           BIGINT NOT NULL DEFAULT 0,
    views           BIGINT NOT NULL DEFAULT 0,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);