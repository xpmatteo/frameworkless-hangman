package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.service.GameResponse;
import it.xpug.frameworkless.hangman.service.HangmanService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Slf4j
public class HangmanRouter {
    private final WebRequest webRequest;
    private final WebResponse webResponse;
    private final HangmanService hangmanService;
    private Matcher matcher;

    public HangmanRouter(WebRequest webRequest, WebResponse webResponse, HangmanService hangmanService) {
        this.webRequest = webRequest;
        this.webResponse = webResponse;
        this.hangmanService = hangmanService;
    }

    public void route() throws IOException {
        try {
            doRoute();
        } catch (ClientError e) {
            webResponse.clientError(e);
        } catch (Exception e) {
            log.error("Internal server error", e);
            webResponse.serverError(e);
        }
    }

    private void doRoute() throws IOException {
        if (post("/hangman/game/([a-f0-9]+)/guesses")) {
            GameResponse gameResponse = hangmanService.guess(pathParameter(1), webRequest.getMandatoryParameter("guess"));
            webResponse.respond(SC_OK, gameResponse);
            return;
        }

        if (get("/hangman/game/([a-f0-9]+)")) {
            webResponse.respond(SC_OK, hangmanService.findGame(pathParameter(1)));
            return;
        }

        if (post("/hangman/game")) {
            Optional<String> word = webRequest.getOptionalParameter("word");

            GameResponse gameResponse =
                    word.map(hangmanService::createNewGame)
                            .orElseGet(() -> hangmanService.createNewGame(null));

            webResponse.respond(SC_CREATED, gameResponse);
            return;
        }

        throw new NotFoundException(webRequest.getPath());
    }

    private String pathParameter(int group) {
        return matcher.group(group);
    }

    private boolean get(String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.GET)
            return false;
        return match(pathTemplate);
    }

    private boolean post(String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.POST)
            return false;
        return match(pathTemplate);
    }

    private boolean match(String pathTemplate) {
        matcher = Pattern.compile(pathTemplate).matcher(webRequest.getPath());
        return matcher.matches();
    }
}
