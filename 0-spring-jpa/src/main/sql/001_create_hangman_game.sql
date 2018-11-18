
CREATE TABLE hangman_games (
    game_id bigint PRIMARY KEY NOT NULL,
    word varchar(255) NOT NULL,
    guesses_remaining smallint NOT NULL,
    hits varchar(255) NOT NULL,
    misses varchar(255) NOT NULL
);

update schema_info set version = 1;