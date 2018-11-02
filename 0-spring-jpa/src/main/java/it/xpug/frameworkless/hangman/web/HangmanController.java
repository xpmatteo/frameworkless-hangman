package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class HangmanController {
    private GameRepository gameRepository;

    @Autowired
    public HangmanController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(path = "/hangman/game", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponse createNewGame(@RequestParam(required = false) String word) {
        Game newGame = (null == word)
                ? gameRepository.createNewGame()
                : gameRepository.createNewGame(word);
        return GameResponse.from(newGame);
    }

    @RequestMapping(path = "/hangman/game/{gameId}", method = RequestMethod.GET)
    public GameResponse findGame(@PathVariable String gameId) {
        long gameIdAsLong = Long.parseLong(gameId, 16);
        return gameRepository.findGame(gameIdAsLong)
                .map(GameResponse::from)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    @RequestMapping(path = "/hangman/game/{gameId}/guesses", method = RequestMethod.POST)
    public GameResponse guess(@PathVariable String gameId, @RequestParam String guess) {
        if (guess.length() != 1)
            throw new InvalidGuessException(guess);
        Game game = gameRepository.findGame(Long.parseLong(gameId, 16))
                .orElseThrow(() -> new GameNotFoundException(gameId));
        game.getPrisoner().guess(guess);
        return GameResponse.from(game);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private class InvalidGuessException extends RuntimeException {
        public InvalidGuessException(String guess) {
            super(String.format("Guess '%s' invalid: must be a single letter", guess));
        }
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private class GameNotFoundException extends RuntimeException {
        public GameNotFoundException(String gameId) {
            super(String.format("Game with id '%s' not found", gameId));
        }
    }
}
