package it.xpug.frameworkless.hangman.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void serialize() throws Exception {
        String gameAsJson = objectMapper.writeValueAsString(new Game(123L));
        assertThat(JsonPath.read(gameAsJson, "gameId"), is("7b"));
    }
}