CREATE table customer
(
    id         BIGSERIAL PRIMARY KEY,
    user_name  TEXT    NOT NULL,
    first_name TEXT    NOT NULL,
    last_name  TEXT    NOT NULL,
    email      TEXT    NOT NULL,
    password   TEXT    NOT NULL,
    gender     TEXT    NOT NULL,
    age        INTEGER NOT NULL
);


