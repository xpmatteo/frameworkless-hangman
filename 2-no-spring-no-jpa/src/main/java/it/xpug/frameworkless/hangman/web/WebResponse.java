package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebResponse {
    private final HttpServletResponse httpServletResponse;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }

    public void respond(int statusCode, Object body) throws IOException {
        objectMapper.writeValue(httpServletResponse.getWriter(), body);
        httpServletResponse.setStatus(statusCode);
        httpServletResponse.setContentType("application/json");
    }

    public void error(Exception exception) {

    }
}
