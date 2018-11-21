package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Guess;
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
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public GameResponse guess(String gameIdAsString, String letter) {
        if (letter.length() != 1)
            throw new InvalidGuessException(letter);
        long gameId = Long.parseLong(gameIdAsString, 16);
        Game game = gameRepository.findGame(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameIdAsString));
        Guess guess = new Guess(letter);
        game.getPrisoner().guess(guess);
        gameRepository.save(gameId, guess);
        return GameResponse.from(game);
    }
}
