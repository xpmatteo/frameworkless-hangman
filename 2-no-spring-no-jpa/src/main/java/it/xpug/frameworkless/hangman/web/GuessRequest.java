package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.domain.Guess;

public class GuessRequest {
    private final WebRequest webRequest;

    public GuessRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    public Long getGameId() {
        String gameId = webRequest.getPathParameter(1);
        return Long.parseLong(gameId, 16);
    }

    public Guess getGuess() {
        String letter = webRequest.getMandatoryParameter("guess");
        return new Guess(letter);
    }

    public String getIpAddress() {
        return webRequest.getIpAddress();
    }

    public String getForwardedFor() {
        return webRequest.getForwardedFor();
    }
}
