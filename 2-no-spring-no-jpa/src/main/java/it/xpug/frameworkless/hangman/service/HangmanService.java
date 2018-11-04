package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.web.ClientError;

import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class HangmanService {
    private GameRepository gameRepository;

    public HangmanService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameResponse createNewGame(String word) {
        Game newGame = (null == word)
                ? gameRepository.createNewGame()
                : gameRepository.createNewGame(word);
        return GameResponse.from(newGame);
    }

    public GameResponse findGame(String gameId) {
        long gameIdAsLong = Long.parseLong(gameId, 16);
        return gameRepository.findGame(gameIdAsLong)
                .map(GameResponse::from)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public GameResponse guess(String gameId, Optional<String> maybeGuess) {
        String guess = maybeGuess.orElseThrow(MissingGuessException::new);
        if (guess.length() != 1)
            throw new InvalidGuessException(guess);
        Game game = gameRepository.findGame(Long.parseLong(gameId, 16))
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.getPrisoner().guess(guess);
        gameRepository.update(game);
        return GameResponse.from(game);
    }

    private class MissingGuessException extends ClientError {
        public MissingGuessException() {
            super(SC_BAD_REQUEST, String.format("Guess parameter missing"));
        }
    }

    private class InvalidGuessException extends ClientError {
        public InvalidGuessException(String guess) {
            super(SC_BAD_REQUEST, String.format("Guess '%s' invalid: must be a single letter", guess));
        }
    }

    private class GameNotFoundException extends ClientError {
        public GameNotFoundException(String gameId) {
            super(SC_NOT_FOUND, String.format("Game with id '%s' not found", gameId));
        }
    }
}
