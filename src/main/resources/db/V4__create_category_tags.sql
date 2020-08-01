create TABLE category(
    id              BIGSERIAL PRIMARY KEY,

    title           VARCHAR(200),
    metaTitle       VARCHAR(200),
    slug            VARCHAR(200),
    content         TEXT,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);

create TABLE postCategory(
    id              BIGSERIAL PRIMARY KEY,

    postId          BIGINT NOT NULL references post,
    categoryId      BIGINT NOT NULL references category,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);
create unique index on postCategory(postId, categoryId);

create TABLE tag(
    id              BIGSERIAL PRIMARY KEY,

    title           VARCHAR(100),
    content         VARCHAR(500),

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);

create index on tag(title);

create TABLE postTag(
    id              BIGSERIAL PRIMARY KEY,

    postId          BIGINT NOT NULL references post,
    tagId           BIGINT NOT NULL references tag,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);
create index on postTag(postId);

