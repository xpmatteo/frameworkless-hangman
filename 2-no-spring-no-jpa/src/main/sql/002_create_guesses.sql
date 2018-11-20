
create table guesses (
    guess_id bigint AUTO_INCREMENT PRIMARY KEY,
    game_id bigint REFERENCES hangman_games(game_id),
    letter char(1) NOT NULL
);

update schema_info set version = 2;