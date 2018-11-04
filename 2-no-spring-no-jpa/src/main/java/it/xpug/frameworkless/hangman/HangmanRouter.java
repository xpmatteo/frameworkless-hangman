package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.web.*;
import it.xpug.frameworkless.hangman.web.toolkit.AbstractRouter;
import it.xpug.frameworkless.hangman.web.toolkit.NotFoundException;
import it.xpug.frameworkless.hangman.web.toolkit.WebRequest;
import it.xpug.frameworkless.hangman.web.toolkit.WebResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
public class HangmanRouter extends AbstractRouter {
    private final HangmanController hangmanController;

    public HangmanRouter(WebRequest webRequest, WebResponse webResponse, HangmanController hangmanController) {
        super(webRequest, webResponse);
        this.hangmanController = hangmanController;
    }

    protected void doRoute() throws IOException {
        if (post("/hangman/game/([a-f0-9]+)/guesses")) {
            GameResponse gameResponse = hangmanController.guess(pathParameter(1), webRequest.getParameter("guess"));
            webResponse.respond(SC_OK, gameResponse);
            return;
        }

        if (get("/hangman/game/([a-f0-9]+)")) {
            webResponse.respond(SC_OK, hangmanController.findGame(pathParameter(1)));
            return;
        }

        if (post("/hangman/game")) {
            Optional<String> word = webRequest.getParameter("word");

            GameResponse gameResponse =
                    word.map(hangmanController::createNewGame)
                            .orElseGet(() -> hangmanController.createNewGame(null));

            webResponse.respond(SC_CREATED, gameResponse);
            return;
        }

        throw new NotFoundException(webRequest.getPath());
    }
}
