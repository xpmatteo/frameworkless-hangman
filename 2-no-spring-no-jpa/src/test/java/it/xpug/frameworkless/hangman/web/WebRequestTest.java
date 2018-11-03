package it.xpug.frameworkless.hangman.web;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static it.xpug.frameworkless.hangman.web.HttpMethod.GET;
import static it.xpug.frameworkless.hangman.web.HttpMethod.OTHER;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class WebRequestTest {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    WebRequest webRequest = new WebRequest(httpServletRequest);

    @Test
    public void getPath() throws Exception {
        when(httpServletRequest.getRequestURI()).thenReturn("/foo/bar");

        assertThat(webRequest.getPath(), is("/foo/bar"));
    }

    @Test
    public void getMethod() throws Exception {
        when(httpServletRequest.getMethod()).thenReturn("get");

        assertThat(webRequest.getMethod(), is(GET));
    }

    @Test
    public void getMethod_notGetOrPost() throws Exception {
        when(httpServletRequest.getMethod()).thenReturn("SOMETHING ELSE");

        assertThat(webRequest.getMethod(), is(OTHER));
    }

    @Test
    public void getParameter() throws Exception {
        when(httpServletRequest.getParameter("found")).thenReturn("here I am");

        assertThat(webRequest.getParameter("found"), is(Optional.of("here I am")));
    }

    @Test
    public void getParameter_notFound() throws Exception {
        assertThat(webRequest.getParameter("non-existing"), is(Optional.empty()));
    }
}