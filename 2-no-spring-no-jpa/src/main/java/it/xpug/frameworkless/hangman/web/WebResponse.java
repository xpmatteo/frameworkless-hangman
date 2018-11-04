package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

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

    public void error(Exception exception) throws IOException {
        Object body = new ExceptionBody("Internal server error", 500, exception.getClass().getName());
        objectMapper.writeValue(httpServletResponse.getWriter(), body);
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.setContentType("application/json");
    }

    @Getter
    static class ExceptionBody {
        String exception;
        String message;
        Integer status;

        public ExceptionBody(String message, Integer status, String exception) {
            this.message = message;
            this.status = status;
            this.exception = exception;
        }
    }
}
