package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.service.GameResponse;
import it.xpug.frameworkless.hangman.service.HangmanService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class HangmanRouterTest {

    private HangmanService hangmanService = mock(HangmanService.class);
    private HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    private WebRequest webRequest = new WebRequest(httpServletRequest);
    private WebResponse webResponse = mock(WebResponse.class);
    private GameResponse aGameResponse = GameResponse.from(new Game(0x77L));

    private HangmanRouter hangmanRouter = new HangmanRouter(webRequest, webResponse, hangmanService);
    private RuntimeException anException = new RuntimeException("abababa");

    @Test
    public void createNewGame() throws Exception {
        post("/hangman/game");
        when(hangmanService.createNewGame(Optional.empty())).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void createNewGameWithSpecifiedWord() throws Exception {
        post("/hangman/game", "word", "foobar");
        when(hangmanService.createNewGame(Optional.of("foobar"))).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void findGame_found() throws Exception {
        get("/hangman/game/abc123");
        when(hangmanService.findGame("abc123")).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(200, aGameResponse);
    }

    @Test
    public void findGame_notFound() throws Exception {
        get("/hangman/game/567def");
        when(hangmanService.findGame("567def")).thenThrow(anException);

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
        post("/hangman/game/123/guesses", "guess", "x");
        when(hangmanService.guess("123", "x")).thenReturn(aGameResponse);

        hangmanRouter.route();

        verify(webResponse).respond(200, aGameResponse);
    }

    private void post(String path, String parameterName, String parameterValue) {
        when(httpServletRequest.getMethod()).thenReturn("POST");
        when(httpServletRequest.getRequestURI()).thenReturn(path);
        when(httpServletRequest.getParameter(parameterName)).thenReturn(parameterValue);
    }

    private void post(String path) {
        when(httpServletRequest.getMethod()).thenReturn("POST");
        when(httpServletRequest.getRequestURI()).thenReturn(path);
    }

    private void get(String path) {
        when(httpServletRequest.getMethod()).thenReturn("GET");
        when(httpServletRequest.getRequestURI()).thenReturn(path);
    }

}