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
}
