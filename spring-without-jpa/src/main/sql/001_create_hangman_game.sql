
create table hangman_games (
    game_id bigint PRIMARY KEY NOT NULL,
    word varchar(255) not null,
    guesses_remaining smallint not null,
    hits varchar(255) not null,
    misses varchar(255) not null
);

update schema_info set version = 1;