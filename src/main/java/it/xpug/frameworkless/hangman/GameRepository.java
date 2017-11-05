package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;

import java.util.Optional;

public interface GameRepository {
    Game createNewGame();

    Optional<Game> findGame(Long gameId);
}
