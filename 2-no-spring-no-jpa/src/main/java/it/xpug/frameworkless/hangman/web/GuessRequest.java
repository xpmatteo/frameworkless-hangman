package it.xpug.frameworkless.hangman.web;

import it.xpug.frameworkless.hangman.domain.Guess;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class GuessRequest {
    private final String gameId;
    private final String letter;
    private final String ipAddress;
    private final String forwardedFor;

    public GuessRequest(String gameId, String letter) {
        this(gameId, letter, null, null);
    }

    public GuessRequest(String gameId, String letter, String ipAddress, String forwardedFor) {
        this.gameId = gameId;
        this.letter = letter;
        this.ipAddress = ipAddress;
        this.forwardedFor = forwardedFor;
    }

    public static GuessRequest from(WebRequest webRequest) {
        String letter = webRequest.getMandatoryParameter("guess");
        if (letter.length() != 1)
            throw new InvalidGuessException(letter);
        return new GuessRequest(
                webRequest.getPathParameter(1),
                letter,
                webRequest.getIpAddress(),
                webRequest.getForwardedFor()
        );
    }

    public Long getGameId() {
        return Long.parseLong(gameId, 16);
    }

    public Guess getGuess() {
        return new Guess(letter);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getForwardedFor() {
        return forwardedFor;
    }
}
