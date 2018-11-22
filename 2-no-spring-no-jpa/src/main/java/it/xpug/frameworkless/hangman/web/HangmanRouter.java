package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.service.GameResponse;
import it.xpug.frameworkless.hangman.service.HangmanService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

/*
    The purpose of this object is to
     - transform a generic WebRequest into a specific call to a method of the Service
     - transform the response from the Service into an appropriate WebResponse
 */
@Slf4j
public class HangmanRouter {
    private final HangmanService hangmanService;

    public HangmanRouter(HangmanService hangmanService) {
        this.hangmanService = hangmanService;
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
        if (webRequest.isPost("/hangman/game/([a-f0-9]+)/guesses")) {
            GameResponse gameResponse = hangmanService.guess(GuessRequest.from(webRequest));
            webResponse.respond(SC_OK, gameResponse);

        } else if (webRequest.isGet("/hangman/game/([a-f0-9]+)")) {
            GameResponse gameResponse = hangmanService.findGame(webRequest.getPathParameter(1));
            webResponse.respond(SC_OK, gameResponse);

        } else if (webRequest.isPost("/hangman/game")) {
            GameResponse gameResponse = hangmanService.createNewGame(webRequest.getOptionalParameter("word"));
            webResponse.respond(SC_CREATED, gameResponse);

        } else {
            throw new NotFoundException(webRequest.getPath());
        }
    }
}
