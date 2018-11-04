package it.xpug.frameworkless.hangman.web.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class AbstractRouter {
    private Matcher matcher;

    public void route(WebRequest webRequest, WebResponse webResponse) throws IOException {
        try {
            doRoute(webRequest, webResponse);
        } catch (ClientError e) {
            webResponse.clientError(e);
        } catch (Exception e) {
            log.error("Internal server error", e);
            webResponse.serverError(e);
        }
    }

    protected abstract void doRoute(WebRequest webRequest, WebResponse webResponse) throws IOException;

    protected String pathParameter(int group) {
        return matcher.group(group);
    }

    protected boolean get(WebRequest webRequest, String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.GET)
            return false;
        return match(webRequest, pathTemplate);
    }

    protected boolean post(WebRequest webRequest, String pathTemplate) {
        if (webRequest.getMethod() != HttpMethod.POST)
            return false;
        return match(webRequest, pathTemplate);
    }

    private boolean match(WebRequest webRequest, String pathTemplate) {
        matcher = Pattern.compile(pathTemplate).matcher(webRequest.getPath());
        return matcher.matches();
    }
}
