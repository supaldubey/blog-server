create TABLE counter(
    id              BIGSERIAL PRIMARY KEY,

    contentId       BIGINT NOT NULL,
    counterType     VARCHAR(25) NOT NULL,
    counterValue    BIGINT NOT NULL DEFAULT 0,

    createdAt TIMESTAMP NOT NULL,
    updatedAt TIMESTAMP NOT NULL
);

CREATE INDEX idx_counter_contentId ON counter (contentId) WHERE (contentId is NOT null);
CREATE INDEX idx_counter_counterType ON counter (counterType) WHERE (counterType is NOT null);

