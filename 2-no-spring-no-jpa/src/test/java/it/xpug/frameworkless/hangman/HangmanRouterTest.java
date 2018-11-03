package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.web.GameResponse;
import it.xpug.frameworkless.hangman.web.HangmanController;
import it.xpug.frameworkless.hangman.web.WebRequest;
import it.xpug.frameworkless.hangman.web.WebResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static it.xpug.frameworkless.hangman.web.HttpMethod.POST;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class HangmanRouterTest {

    private HangmanController hangmanController = mock(HangmanController.class);
    private WebRequest webRequest = mock(WebRequest.class);
    private WebResponse webResponse = mock(WebResponse.class);

    private HangmanRouter hangmanRouter = new HangmanRouter(hangmanController);

//    @Before
//    public void setUp() throws Exception {
//        when(webRequest.getParameter(anyString())).thenThrow(new RuntimeException("unexpected call"));
//    }

    @Test
    public void createNewGame() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game");
        when(webRequest.getMethod()).thenReturn(POST);
        when(webRequest.getParameter("word")).thenReturn(Optional.empty());
        GameResponse gameResponse = GameResponse.from(new Game(0x77L));
        when(hangmanController.createNewGame(null)).thenReturn(gameResponse);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).respond(201, gameResponse);
    }

    @Test
    public void createNewGameWithSpecifiedWord() throws Exception {
        when(webRequest.getPath()).thenReturn("/hangman/game");
        when(webRequest.getMethod()).thenReturn(POST);
        when(webRequest.getParameter("word")).thenReturn(Optional.of("foobar"));
        GameResponse gameResponse = GameResponse.from(new Game(0x77L));
        when(hangmanController.createNewGame("foobar")).thenReturn(gameResponse);

        hangmanRouter.route(webRequest, webResponse);

        verify(webResponse).respond(201, gameResponse);
    }
}