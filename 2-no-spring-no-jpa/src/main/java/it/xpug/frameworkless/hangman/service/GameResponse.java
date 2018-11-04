package it.xpug.frameworkless.hangman.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.util.ToHexSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GameResponse {
    @JsonSerialize(using= ToHexSerializer.class)
    private long gameId;
    private Set<String> hits;
    private Set<String> misses;
    private String state;
    private int guessesRemaining;
    private String word;

    public static GameResponse from(Game game) {
        GameResponse gameResponse = new GameResponse();
        gameResponse.gameId = game.getGameId();

        Prisoner prisoner = game.getPrisoner();
        gameResponse.state = prisoner.getState();
        gameResponse.hits = prisoner.getHits();
        gameResponse.misses = prisoner.getMisses();
        gameResponse.guessesRemaining = prisoner.getGuessesRemaining();
        gameResponse.word = prisoner.getMaskedWord();

        return gameResponse;
    }
}
