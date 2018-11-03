package it.xpug.frameworkless.hangman.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class WebRequest {
    private final HttpServletRequest httpServletRequest;

    public WebRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getPath() {
        return httpServletRequest.getRequestURI();
    }

    public HttpMethod getMethod() {
        try {
            return HttpMethod.valueOf(httpServletRequest.getMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            return HttpMethod.OTHER;
        }
    }

    public Optional<String> getParameter(String name) {
        return Optional.ofNullable(httpServletRequest.getParameter(name));
    }
}
