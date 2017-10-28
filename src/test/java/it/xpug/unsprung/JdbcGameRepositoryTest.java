package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import it.xpug.unsprung.domain.GameIdGenerator;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcGameRepositoryTest {

    GameIdGenerator gameIdGenerator = mock(GameIdGenerator.class);
    JdbcGameRepository jdbcGameRepository = new JdbcGameRepository(gameIdGenerator);

    @Test
    public void createNewGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn("abc123");

        Game game = jdbcGameRepository.createNewGame();

        assertThat(game.getGameId(), is("abc123"));
    }


}