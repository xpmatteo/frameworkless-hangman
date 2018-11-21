package it.xpug.frameworkless.hangman.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebRequest {
    private final HttpServletRequest httpServletRequest;
    private Matcher matcher;

    public WebRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getPath() {
        return httpServletRequest.getRequestURI();
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

    public HttpMethod getMethod() {
        try {
            return HttpMethod.valueOf(httpServletRequest.getMethod().toUpperCase());
        } catch (IllegalArgumentException e) {
            return HttpMethod.OTHER;
        }
    }

    public boolean isGet(String pathTemplate) {
        if (getMethod() != HttpMethod.GET)
            return false;
        return match(pathTemplate);
    }

    public boolean isPost(String pathTemplate) {
        if (getMethod() != HttpMethod.POST)
            return false;
        return match(pathTemplate);
    }

    public String getPathParameter(int group) {
        return matcher.group(group);
    }

    private boolean match(String pathTemplate) {
        matcher = Pattern.compile(pathTemplate).matcher(getPath());
        return matcher.matches();
    }

    public String getIpAddress() {
        return httpServletRequest.getRemoteAddr();
    }

    public String getForwardedFor() {
        return httpServletRequest.getHeader("x-forwarded-for");
    }
}
