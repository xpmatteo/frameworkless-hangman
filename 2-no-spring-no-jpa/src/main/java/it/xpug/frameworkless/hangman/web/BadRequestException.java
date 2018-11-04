package it.xpug.frameworkless.hangman.web;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class BadRequestException extends ClientError {
    public BadRequestException(String message) {
        super(SC_BAD_REQUEST, message);
    }
}
