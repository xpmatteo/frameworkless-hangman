
alter table guesses add column ip_address varchar(39) NULL;
alter table guesses add column forwarded_for varchar(255) NULL;
alter table guesses add column created_at timestamp NOT NULL DEFAULT current_timestamp();
alter table guesses add column updated_at timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp();

alter table hangman_games add column created_at timestamp NOT NULL DEFAULT current_timestamp();
alter table hangman_games add column updated_at timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp();


update schema_info set version = 3;