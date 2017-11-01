package it.xpug.unsprung.hangman;

import it.xpug.unsprung.hangman.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping(value = "/hangman", produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class HangmanController {
    private GameRepository gameRepository;

    @Autowired
    public HangmanController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(path = "/game", method = RequestMethod.POST)
    public ResponseEntity<Game> createNewGame() {
        Game newGame = gameRepository.createNewGame();
        return new ResponseEntity<Game>(newGame, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/game/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<Game> findGame(@PathVariable String gameId) {
        Optional<Game> maybeGame = gameRepository.findGame(Long.parseLong(gameId, 16));
        if (maybeGame.isPresent())
            return new ResponseEntity<Game>(maybeGame.get(), HttpStatus.OK);
        return new ResponseEntity<Game>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "/game/{gameId}/guesses", method = RequestMethod.POST)
    public ResponseEntity<Game> guess(@PathVariable String gameId, @RequestParam String guess) {
        Optional<Game> maybeGame = gameRepository.findGame(Long.parseLong(gameId, 16));
        Game game = maybeGame.get();
        game.getPrisoner().guess(guess);
        return new ResponseEntity<Game>(game, HttpStatus.OK);
    }
}
