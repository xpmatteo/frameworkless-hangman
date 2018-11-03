package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.web.GameResponse;
import it.xpug.frameworkless.hangman.web.HangmanController;
import it.xpug.frameworkless.hangman.web.WebRequest;
import it.xpug.frameworkless.hangman.web.WebResponse;
import org.junit.Test;

import java.util.Optional;

import static it.xpug.frameworkless.hangman.web.HttpMethod.GET;
import static it.xpug.frameworkless.hangman.web.HttpMethod.POST;
import static org.mockito.Mockito.*;

public class HangmanRouterTest {

    private HangmanController hangmanController = mock(HangmanController.class);
    private WebRequest webRequest = mock(WebRequest.class);
    private WebResponse webResponse = mock(WebResponse.class);
    private GameResponse aGameResponse = GameResponse.from(new Game(0x77L));

    private HangmanRouter hangmanRouter = new HangmanRouter(hangmanController);
    private RuntimeException anException = new RuntimeException("abababa");

    @Test
    public void createNewGame() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game");
        when(webRequest.getMethod()).thenReturn(POST);
        when(webRequest.getParameter("word")).thenReturn(Optional.empty());
        when(hangmanController.createNewGame(null)).thenReturn(aGameResponse);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void createNewGameWithSpecifiedWord() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game");
        when(webRequest.getMethod()).thenReturn(POST);
        when(webRequest.getParameter("word")).thenReturn(Optional.of("foobar"));
        when(hangmanController.createNewGame("foobar")).thenReturn(aGameResponse);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).respond(201, aGameResponse);
    }

    @Test
    public void findGame_found() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game/abc123");
        when(webRequest.getMethod()).thenReturn(GET);
        when(hangmanController.findGame("abc123")).thenReturn(aGameResponse);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).respond(200, aGameResponse);
    }

    @Test
    public void findGame_notFound() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game/abc123");
        when(webRequest.getMethod()).thenReturn(GET);
        when(hangmanController.findGame("abc123")).thenThrow(anException);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).error(anException);
    }

}