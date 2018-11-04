package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.web.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
public class HangmanRouter {

    private final HangmanController hangmanController;
    private Matcher matcher;

    public HangmanRouter(HangmanController hangmanController) {
        this.hangmanController = hangmanController;
    }

    public void route(WebRequest webRequest, WebResponse webResponse) throws IOException {
        try {
            doRoute(webRequest, webResponse);
        } catch (ClientError e) {
            webResponse.clientError(e);
        } catch (Exception e) {
            log.error("Internal server error", e);
            webResponse.serverError(e);
        }
    }

    private void doRoute(WebRequest webRequest, WebResponse webResponse) throws IOException {

        if (post(webRequest, "/hangman/game/([a-f0-9]+)/guesses")) {
            GameResponse gameResponse = hangmanController.guess(pathParameter(1), webRequest.getParameter("guess"));
            webResponse.respond(SC_OK, gameResponse);
            return;
        }

        if (get(webRequest, "/hangman/game/([a-f0-9]+)")) {
            webResponse.respond(SC_OK, hangmanController.findGame(pathParameter(1)));
            return;
        }

        if (post(webRequest, "/hangman/game")) {
            Optional<String> word = webRequest.getParameter("word");

            GameResponse gameResponse =
                    word.map(hangmanController::createNewGame)
                            .orElseGet(() -> hangmanController.createNewGame(null));

            webResponse.respond(SC_CREATED, gameResponse);
            return;
        }

        throw new NotFoundException(webRequest.getPath());
    }

    private String pathParameter(int group) {
        return matcher.group(group);
    }

    private boolean get(WebRequest webRequest, String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.GET)
            return false;
        return match(webRequest, pathTemplate);
    }

    private boolean post(WebRequest webRequest, String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.POST)
            return false;
        return match(webRequest, pathTemplate);
    }

    private boolean match(WebRequest webRequest, String pathTemplate) {
        matcher = Pattern.compile(pathTemplate).matcher(webRequest.getPath());
        return matcher.matches();
    }

}
