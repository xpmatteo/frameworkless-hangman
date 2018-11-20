package it.xpug.frameworkless.hangman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Guess;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GameResponseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    GameResponse gameResponse;

    @Before
    public void setUp() throws Exception {
        Game game = new Game(0x7bL, new Prisoner("fooobar"));
        game.getPrisoner().guess("o");
        game.getPrisoner().guess("x");
        gameResponse = GameResponse.from(game);
    }

    @Test
    public void createFromGame() throws Exception {
        assertEquals(123L, gameResponse.getGameId());
        assertEquals(singleton(new Guess("o")), gameResponse.getHits());
        assertEquals(singleton(new Guess("x")), gameResponse.getMisses());
        assertEquals("help", gameResponse.getState());
        assertEquals(16, gameResponse.getGuessesRemaining());
        assertEquals("*ooo***", gameResponse.getWord());
    }

    @Test
    public void serializeToJson() throws Exception {
        String gameAsJson = objectMapper.writeValueAsString(gameResponse);

        String expected = "{" +
                "\"gameId\":\"7b\"," +
                "\"hits\":[\"o\"]," +
                "\"misses\":[\"x\"]," +
                "\"state\":\"help\"," +
                "\"guessesRemaining\":16," +
                "\"word\":\"*ooo***\"}";
        assertThat(gameAsJson, is(expected));
    }

}