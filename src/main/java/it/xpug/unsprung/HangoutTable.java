package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HangoutTable extends CrudRepository<Game, Long> {}
