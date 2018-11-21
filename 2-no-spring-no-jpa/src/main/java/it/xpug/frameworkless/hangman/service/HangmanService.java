package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.web.GuessRequest;

import java.util.Optional;

public class HangmanService {
    private GameRepository gameRepository;

    public HangmanService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public GameResponse createNewGame(Optional<String> optionalWord) {
        Game newGame = optionalWord
                .map(word -> gameRepository.createNewGame(word))
                .orElseGet(() -> gameRepository.createNewGame());
        return GameResponse.from(newGame);
    }

    public GameResponse findGame(String gameId) {
        long gameIdAsLong = Long.parseLong(gameId, 16);
        return gameRepository.findGame(gameIdAsLong)
                .map(GameResponse::from)
                .orElseThrow(GameNotFoundException::new);
    }

    public GameResponse guess(GuessRequest guessRequest) {
        Long gameId = guessRequest.getGameId();
        Game game = gameRepository.findGame(gameId)
                .orElseThrow(GameNotFoundException::new);
        game.getPrisoner().guess(guessRequest.getGuess());
        gameRepository.save(gameId, guessRequest.getGuess());
        return GameResponse.from(game);
    }
}
