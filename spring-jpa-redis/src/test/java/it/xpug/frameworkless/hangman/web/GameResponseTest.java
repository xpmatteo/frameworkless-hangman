package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.junit.Test;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GameResponseTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void serializeToJson() throws Exception {
        GameResponse gameResponse = new GameResponse();
        gameResponse.setGameId((long) 0x7b);
        String gameAsJson = objectMapper.writeValueAsString(gameResponse);
        assertThat(JsonPath.read(gameAsJson, "gameId"), is("7b"));
    }

    @Test
    public void createFromGame() throws Exception {
        Game game = new Game(123L, new Prisoner("fooobar"));
        game.getPrisoner().guess("o");
        game.getPrisoner().guess("x");

        GameResponse gameResponse = GameResponse.from(game);

        assertEquals(123L, gameResponse.getGameId());
        assertEquals(singleton("o"), gameResponse.getHits());
        assertEquals(singleton("x"), gameResponse.getMisses());
        assertEquals("help", gameResponse.getState());
        assertEquals(16, gameResponse.getGuessesRemaining());
        assertEquals("*ooo***", gameResponse.getWord());
    }

}