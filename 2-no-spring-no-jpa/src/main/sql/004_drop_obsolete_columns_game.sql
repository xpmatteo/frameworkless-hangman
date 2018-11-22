
alter table hangman_games drop column guesses_remaining;
alter table hangman_games drop column hits;
alter table hangman_games drop column misses;

update schema_info set version = 4;
