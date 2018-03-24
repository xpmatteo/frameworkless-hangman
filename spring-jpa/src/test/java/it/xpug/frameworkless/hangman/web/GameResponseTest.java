package it.xpug.frameworkless.hangman.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import it.xpug.frameworkless.hangman.domain.Game;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
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

}