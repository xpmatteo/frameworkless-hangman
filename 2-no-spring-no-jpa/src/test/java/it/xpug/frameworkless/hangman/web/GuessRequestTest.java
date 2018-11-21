package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.domain.Guess;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class GuessRequestTest {
    WebRequest webRequest = mock(WebRequest.class);

    @Before
    public void setUp() throws Exception {
        when(webRequest.getMandatoryParameter("guess")).thenReturn("x");
    }

    @Test
    public void getGameId() throws Exception {
        when(webRequest.getPathParameter(1)).thenReturn("1234");
        GuessRequest gameRequest = GuessRequest.from(webRequest);

        assertThat(gameRequest.getGameId(), is(0x1234L));
    }

    @Test
    public void getGuess() throws Exception {
        GuessRequest gameRequest = GuessRequest.from(webRequest);

        assertThat(gameRequest.getGuess(), is(new Guess("x")));
    }

    @Test
    public void getIpAddress() throws Exception {
        when(webRequest.getIpAddress()).thenReturn("1.2.3.4");
        GuessRequest gameRequest = GuessRequest.from(webRequest);

        assertThat(gameRequest.getIpAddress(), is("1.2.3.4"));
    }

    @Test
    public void getForwardedFor() throws Exception {
        when(webRequest.getForwardedFor()).thenReturn("aaa");
        GuessRequest gameRequest = GuessRequest.from(webRequest);

        assertThat(gameRequest.getForwardedFor(), is("aaa"));
    }

    @Test(expected = InvalidGuessException.class)
    public void guess_parameterTooBig() throws Exception {
        when(webRequest.getMandatoryParameter("guess")).thenReturn("ab");

        GuessRequest.from(webRequest);
    }

}