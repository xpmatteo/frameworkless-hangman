package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.web.*;
import it.xpug.frameworkless.hangman.web.toolkit.NotFoundException;
import it.xpug.frameworkless.hangman.web.toolkit.WebRequest;
import it.xpug.frameworkless.hangman.web.toolkit.WebResponse;
import org.junit.Test;

import java.util.Optional;

import static it.xpug.frameworkless.hangman.web.toolkit.HttpMethod.GET;
import static it.xpug.frameworkless.hangman.web.toolkit.HttpMethod.POST;
import static org.mockito.Mockito.*;

public class HangmanRouterTest {

    private HangmanController hangmanController = mock(HangmanController.class);
    private WebRequest webRequest = mock(WebRequest.class);
    private WebResponse webResponse = mock(WebResponse.class);
    private GameResponse aGameResponse = GameResponse.from(new Game(0x77L));

    private HangmanRouter hangmanRouter = new HangmanRouter(webRequest, webResponse, hangmanController);
    private RuntimeException anException = new RuntimeException("abababa");

    @Test
    public void createNewGame() throws Exception {
        post("/hangman/game", "word", Optional.empty());
        when(hangmanController.createNewGame(null)).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void createNewGameWithSpecifiedWord() throws Exception {
        post("/hangman/game", "word", Optional.of("foobar"));
        when(hangmanController.createNewGame("foobar")).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void findGame_found() throws Exception {
        get("/hangman/game/abc123");
        when(hangmanController.findGame("abc123")).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(200, aGameResponse);
    }

    @Test
    public void findGame_notFound() throws Exception {
        get("/hangman/game/567def");
        when(hangmanController.findGame("567def")).thenThrow(anException);

        hangmanRouter.route();

        verify(webResponse).serverError(anException);
    }

    @Test
    public void pathNotFound() throws Exception {
        get("/foobar");

        hangmanRouter.route();

        verify(webResponse).clientError(new NotFoundException("/foobar"));
    }

    @Test
    public void guess() throws Exception {
        post("/hangman/game/123/guesses", "guess", Optional.of("x"));
        when(hangmanController.guess("123", Optional.of("x"))).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(200, aGameResponse);
    }

    private void post(String path, String parameterName, Optional<String> parameterValue) {
        when(webRequest.getMethod()).thenReturn(POST);
        when(webRequest.getPath()).thenReturn(path);
        when(webRequest.getParameter(parameterName)).thenReturn(parameterValue);
    }

    private void get(String path) {
        when(webRequest.getMethod()).thenReturn(GET);
        when(webRequest.getPath()).thenReturn(path);
    }

}