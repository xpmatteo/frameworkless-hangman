package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class JdbcGameRepositoryTest {

    @Autowired
    DataSource dataSource;

    @MockBean
    GameIdGenerator gameIdGenerator;

    @Autowired
    JdbcGameRepository jdbcGameRepository;

    Connection connection;

    @Before
    public void setUp() throws SQLException {
        connection = dataSource.getConnection();
        connection.createStatement().execute("delete from hangman_games");
    }

    @Test
    public void createNewGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn(123L);
        assertThat(gameCount(), is(0));

        Game game = jdbcGameRepository.createNewGame();

        assertThat(game.getGameId(), is(123L));
        assertThat(gameCount(), is(1));
    }

    @Test
    public void findGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn(789L);
        jdbcGameRepository.createNewGame();

        Optional<Game> game = jdbcGameRepository.findGame(789L);

        assertThat("not present", game.isPresent(), is(true));
        assertThat(game.get().getGameId(), is(789L));
    }

    @Test
    public void saveAndLoad() throws Exception {
        Game original = new Game(42L, new Prisoner("foobar"));
        original.getPrisoner().guess("x");
        original.getPrisoner().guess("y");
        original.getPrisoner().guess("f");
        original.getPrisoner().guess("o");
        jdbcGameRepository.save(original);

        Optional<Game> game = jdbcGameRepository.findGame(42L);

        assertThat("not present", game.isPresent(), is(true));
        assertThat(game.get().getPrisoner(), is(original.getPrisoner()));
    }

    @Test
    public void gameNotFound() throws Exception {
        Optional<Game> game = jdbcGameRepository.findGame(9869L);

        assertThat("not present", game.isPresent(), is(false));
    }

    private int gameCount() throws SQLException {
        String sql = "select count(*) from hangman_games";
        return (int) (long) select(sql);
    }

    private Object select(String sql) throws SQLException {
        return new QueryRunner().query(connection, sql, new ScalarHandler<>());
    }


}