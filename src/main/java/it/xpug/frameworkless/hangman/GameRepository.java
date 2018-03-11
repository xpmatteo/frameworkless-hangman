package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;

import java.util.Optional;

public interface GameRepository {
    Game createNewGame();

    void update(Game game);

    Optional<Game> findGame(Long gameId);
}
