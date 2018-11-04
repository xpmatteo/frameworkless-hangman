package it.xpug.frameworkless.hangman.service;

import it.xpug.frameworkless.hangman.web.ClientError;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

class GameNotFoundException extends ClientError {
    public GameNotFoundException(String gameId) {
        super(SC_NOT_FOUND, String.format("Game with id '%s' not found", gameId));
    }
}
