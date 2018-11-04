package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.FakeHttpServletResponse;
import lombok.Getter;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

public class WebResponseTest {

    @Getter
    static class SampleObject {
        String foo;
        Integer bar;
        public SampleObject(String foo, Integer bar) {
            this.foo = foo;
            this.bar = bar;
        }
    }

    FakeHttpServletResponse fakeHttpServletResponse = new FakeHttpServletResponse();
    WebResponse webResponse = new WebResponse(fakeHttpServletResponse);

    @Test
    public void respondWithObject_serializesBodyWithJson() throws Exception {
        webResponse.respond(anyStatus(), new SampleObject("FOO", 123));

        assertThat(fakeHttpServletResponse.getBodyAsString(), is("{\"foo\":\"FOO\",\"bar\":123}"));
    }

    @Test
    public void respondWithObject_savesStatusCode() throws Exception {
        webResponse.respond(201, anyBody());

        assertThat(fakeHttpServletResponse.getStatus(), is(201));
    }

    @Test
    public void respondWithObject_setsContentType() throws Exception {
        webResponse.respond(anyStatus(), anyBody());

        assertThat(fakeHttpServletResponse.getHeader("Content-Type"), is("application/json"));
    }

    private String anyBody() {
        return "body";
    }

    private int anyStatus() {
        return 201;
    }
}