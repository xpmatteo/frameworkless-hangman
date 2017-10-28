
create table hangman_games (
    game_id bigint PRIMARY KEY NOT NULL,
    word varchar(255) not null,
    guesses_remaining smallint not null
);

update schema_info set version = 1;