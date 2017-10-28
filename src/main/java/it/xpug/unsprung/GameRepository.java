package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;

import java.util.Optional;

public interface GameRepository {
    Game createNewGame();

    Optional<Game> findGame(Long gameId);
}
