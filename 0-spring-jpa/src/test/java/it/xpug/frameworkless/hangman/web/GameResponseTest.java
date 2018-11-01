package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GameResponseTest {

    ObjectMapper objectMapper = new ObjectMapper();
    GameResponse gameResponse;

    @Before
    public void setUp() throws Exception {
        Game game = new Game(0x7bl, new Prisoner("fooobar"));
        game.getPrisoner().guess("o");
        game.getPrisoner().guess("x");
        gameResponse = GameResponse.from(game);
    }

    @Test
    public void serializeToJson() throws Exception {
        String gameAsJson = objectMapper.writeValueAsString(gameResponse);

        assertThat(JsonPath.read(gameAsJson, "gameId"), is("7b"));
        assertThat(JsonPath.read(gameAsJson, "hits[0]"), is("o"));
    }

    @Test
    public void createFromGame() throws Exception {
        assertEquals(123L, gameResponse.getGameId());
        assertEquals(singleton("o"), gameResponse.getHits());
        assertEquals(singleton("x"), gameResponse.getMisses());
        assertEquals("help", gameResponse.getState());
        assertEquals(16, gameResponse.getGuessesRemaining());
        assertEquals("*ooo***", gameResponse.getWord());
    }

}