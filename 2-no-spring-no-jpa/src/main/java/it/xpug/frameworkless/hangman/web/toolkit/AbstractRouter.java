package it.xpug.frameworkless.hangman.web.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class AbstractRouter {
    protected final WebRequest webRequest;
    protected final WebResponse webResponse;
    private Matcher matcher;

    protected AbstractRouter(WebRequest webRequest, WebResponse webResponse) {
        this.webRequest = webRequest;
        this.webResponse = webResponse;
    }

    public void route() throws IOException {
        try {
            doRoute();
        } catch (ClientError e) {
            webResponse.clientError(e);
        } catch (Exception e) {
            log.error("Internal server error", e);
            webResponse.serverError(e);
        }
    }

    protected abstract void doRoute() throws IOException;

    protected String pathParameter(int group) {
        return matcher.group(group);
    }

    protected boolean get(String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.GET)
            return false;
        return match(pathTemplate);
    }

    protected boolean post(String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.POST)
            return false;
        return match(pathTemplate);
    }

    private boolean match(String pathTemplate) {
        matcher = Pattern.compile(pathTemplate).matcher(webRequest.getPath());
        return matcher.matches();
    }
}
