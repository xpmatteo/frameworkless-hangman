package it.xpug.unsprung.hangman;

import it.xpug.unsprung.hangman.domain.Game;

import java.util.Optional;

public interface GameRepository {
    Game createNewGame();

    Optional<Game> findGame(Long gameId);
}
