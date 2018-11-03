package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.web.GameResponse;
import it.xpug.frameworkless.hangman.web.HangmanController;
import it.xpug.frameworkless.hangman.web.WebRequest;
import it.xpug.frameworkless.hangman.web.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

public class HangmanRouter {

    private final HangmanController hangmanController;

    public HangmanRouter(HangmanController hangmanController) {
        this.hangmanController = hangmanController;
    }

    public void route(WebRequest webRequest, WebResponse webResponse) {
        Optional<String> word = webRequest.getParameter("word");

        GameResponse gameResponse =
                word.map(hangmanController::createNewGame)
                        .orElseGet(() -> hangmanController.createNewGame(null));

        webResponse.respond(SC_CREATED, gameResponse);
    }

}
