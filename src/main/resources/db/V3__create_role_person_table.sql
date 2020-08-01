create TABLE role(
    id              BIGSERIAL PRIMARY KEY,

    roleName        VARCHAR(80),

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);

create TABLE personRole(
    id              BIGSERIAL PRIMARY KEY,

    personId        BIGINT NOT NULL references Person,
    roleId          BIGINT NOT NULL references Role,

    createdAt       TIMESTAMP NOT NULL,
    updatedAt       TIMESTAMP NOT NULL
);

create index on personRole(personId);

