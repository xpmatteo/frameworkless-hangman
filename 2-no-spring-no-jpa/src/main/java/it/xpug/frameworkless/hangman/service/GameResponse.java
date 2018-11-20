package it.xpug.frameworkless.hangman.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Guess;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonSerialize(using = GameResponseSerializer.class)
public class GameResponse {
    private long gameId;
    private Set<Guess> hits;
    private Set<Guess> misses;
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

class GameResponseSerializer extends StdSerializer<GameResponse> {

    protected GameResponseSerializer() {
        super(GameResponse.class);
    }

    @Override
    public void serialize(GameResponse gameResponse, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("gameId");
        gen.writeString(Long.toHexString(gameResponse.getGameId()));
        gen.writeFieldName("hits");
        gen.writeStartArray();
        for (Guess guess: gameResponse.getHits()) {
            gen.writeString(guess.getLetter());
        }
        gen.writeEndArray();
        gen.writeFieldName("misses");
        gen.writeStartArray();
        for (Guess guess: gameResponse.getMisses()) {
            gen.writeString(guess.getLetter());
        }
        gen.writeEndArray();
        gen.writeFieldName("state");
        gen.writeString(gameResponse.getState());
        gen.writeFieldName("guessesRemaining");
        gen.writeNumber(gameResponse.getGuessesRemaining());
        gen.writeFieldName("word");
        gen.writeString(gameResponse.getWord());
        gen.writeEndObject();
    }
}
