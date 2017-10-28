package it.xpug.unsprung.hangman;

import it.xpug.unsprung.hangman.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HangoutTable extends CrudRepository<Game, Long> {}
