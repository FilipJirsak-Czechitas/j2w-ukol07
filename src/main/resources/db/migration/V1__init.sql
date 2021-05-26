CREATE TABLE post
(
    id IDENTITY,
    slug      VARCHAR(100) NOT NULL UNIQUE,
    author    VARCHAR(100) NOT NULL,
    title     VARCHAR(255) NOT NULL,
    perex     TEXT         NOT NULL,
    body      TEXT         NOT NULL,
    published DATE
);

CREATE
    INDEX ON post(published);
