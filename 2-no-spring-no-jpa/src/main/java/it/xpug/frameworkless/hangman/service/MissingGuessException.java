package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.web.ClientError;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

class MissingGuessException extends ClientError {
    public MissingGuessException() {
        super(SC_BAD_REQUEST, String.format("Guess parameter missing"));
    }
}
