package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.web.ClientError;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

class GameNotFoundException extends ClientError {
    public GameNotFoundException() {
        super(SC_NOT_FOUND, "Game not found");
    }
}
