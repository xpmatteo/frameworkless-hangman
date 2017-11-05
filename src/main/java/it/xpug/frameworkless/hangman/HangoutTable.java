package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HangoutTable extends CrudRepository<Game, Long> {}
