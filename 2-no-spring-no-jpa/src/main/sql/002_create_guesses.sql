
create table guesses (
    guess_id bigint PRIMARY KEY NOT NULL,
    hangman_game_id bigint REFERENCES hangman_games(game_id),
    guess char(1) NOT NULL
);

update schema_info set version = 2;