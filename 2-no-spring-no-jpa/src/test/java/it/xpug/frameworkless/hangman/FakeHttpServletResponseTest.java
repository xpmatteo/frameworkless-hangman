package it.xpug.frameworkless.hangman;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class FakeHttpServletResponseTest {
    FakeHttpServletResponse response = new FakeHttpServletResponse();

    @Test
    public void getAndSetStatus() throws Exception {
        response.setStatus(201);

        assertThat(response.getStatus(), is(201));
    }

    @Test
    public void writeBodyAndReturnIt() throws Exception {
        PrintWriter writer = response.getWriter();

        writer.write("foo, bar, baz");

        assertThat(response.getBodyAsString(), is("foo, bar, baz"));
    }

    @Test
    public void setsAndRememberHeaders() throws Exception {
        response.addHeader("Foo", "bar");

        assertThat(response.getHeader("fOO"), is("bar"));
    }

    @Test
    public void setsAndRememberContentType() throws Exception {
        response.setContentType("foo/bar");

        assertThat(response.getHeader("content-type"), is("foo/bar"));
        assertThat(response.getContentType(), is("foo/bar"));
    }

}
