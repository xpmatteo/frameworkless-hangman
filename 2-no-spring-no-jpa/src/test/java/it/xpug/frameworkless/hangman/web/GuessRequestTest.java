package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.domain.Guess;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class GuessRequestTest {
    WebRequest webRequest = mock(WebRequest.class);
    GuessRequest gameRequest = new GuessRequest(webRequest);

    @Test
    public void getGameId() throws Exception {
        when(webRequest.getPathParameter(1)).thenReturn("1234");

        assertThat(gameRequest.getGameId(), is(0x1234L));
    }

    @Test
    public void getGuess() throws Exception {
        when(webRequest.getMandatoryParameter("guess")).thenReturn("x");

        assertThat(gameRequest.getGuess(), is(new Guess("x")));
    }

    @Test
    public void getIpAddress() throws Exception {
        when(webRequest.getIpAddress()).thenReturn("1.2.3.4");

        assertThat(gameRequest.getIpAddress(), is("1.2.3.4"));
    }

    @Test
    public void getForwardedFor() throws Exception {
        when(webRequest.getForwardedFor()).thenReturn("aaa");

        assertThat(gameRequest.getForwardedFor(), is("aaa"));
    }
}