package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/hangout", produces = MediaType.APPLICATION_JSON_VALUE)
public class CodeBreakerController {
    private GameRepository gameRepository;

    @Autowired
    public CodeBreakerController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(path = "/game", method = RequestMethod.POST)
    public ResponseEntity<Game> createNewGame() {
        Game newGame = gameRepository.createNewGame();
        return new ResponseEntity<Game>(newGame, HttpStatus.CREATED);
    }
}
