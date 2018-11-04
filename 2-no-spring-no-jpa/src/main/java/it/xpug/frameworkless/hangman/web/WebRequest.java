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

    public Optional<String> getOptionalParameter(String name) {
        return Optional.ofNullable(httpServletRequest.getParameter(name));
    }

    public String getMandatoryParameter(String name) {
        String value = httpServletRequest.getParameter(name);
        if (null == value)
            throw new BadRequestException(String.format("Parameter <%s> is mandatory", name));
        return value;
    }
}
