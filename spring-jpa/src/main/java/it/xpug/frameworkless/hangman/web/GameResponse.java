package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.util.ToHexSerializer;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Set;

@Data
public class GameResponse {
    @JsonSerialize(using=ToHexSerializer.class)
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
        gameResponse.word = prisoner.getMaskedWord();
        BeanUtils.copyProperties(prisoner, gameResponse);

        return gameResponse;
    }
}
