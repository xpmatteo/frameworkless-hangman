package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.util.ToHexSerializer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class GameResponse {
    @JsonSerialize(using=ToHexSerializer.class)
    private Long gameId;
    private Prisoner prisoner;

    public static GameResponse from(Game game) {
        GameResponse gameResponse = new GameResponse();
        BeanUtils.copyProperties(game, gameResponse);
        return gameResponse;
    }
}
