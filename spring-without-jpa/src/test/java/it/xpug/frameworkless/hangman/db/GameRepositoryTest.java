package it.xpug.frameworkless.hangman.db;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.db.TestDataSource;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameRepositoryTest {

    DataSource dataSource = new TestDataSource();

    GameIdGenerator gameIdGenerator = mock(GameIdGenerator.class);

    GameRepository gameRepository = new GameRepository(gameIdGenerator, dataSource);

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

        Game game = gameRepository.createNewGame();

        assertThat(game.getGameId(), is(123L));
        assertThat(gameCount(), is(1));
    }

    @Test
    public void findGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn(789L);
        gameRepository.createNewGame();

        Optional<Game> game = gameRepository.findGame(789L);

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
        gameRepository.create(original);

        Optional<Game> game = gameRepository.findGame(42L);

        assertThat("not present", game.isPresent(), is(true));
        assertThat(game.get().getPrisoner(), is(original.getPrisoner()));
    }

    @Test
    public void updateGame() throws Exception {
        Game original = new Game(42L, new Prisoner("foobar"));
        gameRepository.create(original);
        Game updated = gameRepository.findGame(42L).get();
        updated.getPrisoner().guess("x");
        gameRepository.update(updated);

        Game found = gameRepository.findGame(42L).get();

        assertThat(found.getPrisoner(), is(updated.getPrisoner()));
    }

    @Test
    public void gameNotFound() throws Exception {
        Optional<Game> game = gameRepository.findGame(9869L);

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