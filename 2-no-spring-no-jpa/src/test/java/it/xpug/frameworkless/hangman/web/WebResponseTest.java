package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.FakeHttpServletResponse;
import lombok.Getter;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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

    FakeHttpServletResponse httpServletResponse = new FakeHttpServletResponse();
    WebResponse webResponse = new WebResponse(httpServletResponse);

    @Test
    public void respondWithObject_serializesBodyWithJson() throws Exception {
        webResponse.respond(anyStatus(), new SampleObject("FOO", 123));

        assertThat(httpServletResponse.getBodyAsString(), is("{\"foo\":\"FOO\",\"bar\":123}"));
    }

    @Test
    public void respondWithObject_savesStatusCode() throws Exception {
        webResponse.respond(201, anyBody());

        assertThat(httpServletResponse.getStatus(), is(201));
    }

    @Test
    public void respondWithObject_setsContentType() throws Exception {
        webResponse.respond(anyStatus(), anyBody());

        assertThat(httpServletResponse.getHeader("Content-Type"), is("application/json"));
    }

    @Test
    public void respondWithOrdinaryException() throws Exception {
        webResponse.serverError(new RuntimeException("foobar"));

        assertThat(httpServletResponse.getStatus(), is(500));
        assertThat(httpServletResponse.getContentType(), is("application/json"));
        String expectedBody = "{" +
                "\"exception\":\"java.lang.RuntimeException\"," +
                "\"message\":\"Internal server error\"," +
                "\"status\":500" +
                "}";
        assertThat(httpServletResponse.getBodyAsString(), is(expectedBody));
    }

    @Test
    public void respondWithClientError() throws Exception {
        webResponse.clientError(new ClientError(400, "you got something wrong"));

        assertThat(httpServletResponse.getStatus(), is(400));
        assertThat(httpServletResponse.getContentType(), is("application/json"));
        String expectedBody = "{" +
                "\"exception\":\"it.xpug.frameworkless.hangman.web.ClientError\"," +
                "\"message\":\"you got something wrong\"," +
                "\"status\":400" +
                "}";
        assertThat(httpServletResponse.getBodyAsString(), is(expectedBody));
    }

    private String anyBody() {
        return "body";
    }

    private int anyStatus() {
        return 201;
    }
}