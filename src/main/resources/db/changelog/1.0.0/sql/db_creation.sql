CREATE TABLE if not exists GameCategory
(
    id   VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

create table if not exists Role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE if not exists Users
(
    id                SERIAL PRIMARY KEY,
    name              VARCHAR(255)        NOT NULL,
    username          VARCHAR(50) UNIQUE  NOT NULL,
    email             VARCHAR(100) UNIQUE NOT NULL,
    password_hash     VARCHAR(255)        NOT NULL,
    registration_date DATE DEFAULT CURRENT_DATE,
    phone             VARCHAR(15),
    role_id           INTEGER REFERENCES Role (id)
);

CREATE TABLE if not exists Game
(
    id                 SERIAL PRIMARY KEY,
    title              VARCHAR(100) NOT NULL,
    description        TEXT,
    category_id        VARCHAR(50) REFERENCES GameCategory (id),
    min_players        INTEGER      NOT NULL,
    max_players        INTEGER      NOT NULL,
    play_time          INTEGER,
    age_recommendation INTEGER
);

CREATE TABLE if not exists Event
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    description  TEXT,
    event_date   TIMESTAMP    NOT NULL,
    organizer_id INTEGER REFERENCES Users (id)
);

CREATE TABLE if not exists Rating
(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER REFERENCES Users (id),
    game_id      INTEGER REFERENCES Game (id),
    rating_score INTEGER NOT NULL CHECK (rating_score BETWEEN 1 AND 5),
    review       TEXT,
    review_date  DATE DEFAULT CURRENT_DATE
);

CREATE TABLE if not exists BookingStatus
(
    id   VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE if not exists Booking
(
    id   SERIAL PRIMARY KEY,
    user_id      INTEGER REFERENCES Users (id),
    game_id      INTEGER REFERENCES Game (id),
    booking_date DATE DEFAULT CURRENT_DATE,
    status       VARCHAR(50) REFERENCES BookingStatus (id),
    return_date  DATE
);

CREATE TABLE if not exists EventParticipation
(
    event_id           INTEGER REFERENCES Event (id),
    user_id            INTEGER REFERENCES Users (id),
    participation_date DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (event_id, user_id)
);

CREATE TABLE if not exists FavoriteGame
(
    user_id    INTEGER REFERENCES Users (id),
    game_id    INTEGER REFERENCES Game (id),
    added_date DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (user_id, game_id)
);