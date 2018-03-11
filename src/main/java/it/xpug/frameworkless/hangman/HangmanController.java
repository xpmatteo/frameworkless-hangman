package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HangmanController {
    private GameRepository gameRepository;

    @Autowired
    public HangmanController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(path = "/hangman/game", method = RequestMethod.POST)
    public ResponseEntity<Game> createNewGame() {
        Game newGame = gameRepository.createNewGame();
        return new ResponseEntity<Game>(newGame, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/hangman/game/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<Game> findGame(@PathVariable String gameId) {
        Optional<Game> maybeGame = gameRepository.findGame(Long.parseLong(gameId, 16));
        if (!maybeGame.isPresent())
            throw  new GameNotFoundException(gameId);
        return new ResponseEntity<Game>(maybeGame.get(), HttpStatus.OK);
    }

    @RequestMapping(path = "/hangman/game/{gameId}/guesses", method = RequestMethod.POST)
    public ResponseEntity<Game> guess(@PathVariable String gameId, @RequestParam String guess) {
        if (guess.length() > 1)
            throw new InvalidGuessException(guess);
        Optional<Game> maybeGame = gameRepository.findGame(Long.parseLong(gameId, 16));
        if (!maybeGame.isPresent())
            throw new GameNotFoundException(gameId);
        Game game = maybeGame.get();
        game.getPrisoner().guess(guess);
        gameRepository.update(game);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
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
