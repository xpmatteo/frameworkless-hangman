package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

@Service
public class JdbcGameRepository implements GameRepository {
    private GameIdGenerator gameIdGenerator;
    private HangoutTable hangoutTable;
    private DataSource dataSource;

    @Autowired
    public JdbcGameRepository(GameIdGenerator gameIdGenerator, HangoutTable hangoutTable, DataSource dataSource) {
        this.gameIdGenerator = gameIdGenerator;
        this.hangoutTable = hangoutTable;
        this.dataSource = dataSource;
    }

    @Override
    public Game createNewGame() {
        Game newGame = new Game(gameIdGenerator.generateGameId(), new Prisoner());
        return save(newGame);
    }

    public Game save(Game newGame) {
        hangoutTable.save(newGame);
        return newGame;
    }

    @Override
    @SneakyThrows
    public Optional<Game> findGame(Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSetHandler<Optional<Game>> handler = new ResultSetHandler<Optional<Game>>() {
                @Override
                public Optional<Game> handle(ResultSet rs) throws SQLException {
                    if (!rs.next())
                        return Optional.empty();

                    Prisoner prisoner = new Prisoner(rs.getString("word"));
                    set(prisoner, "guessesRemaining", rs.getObject("guesses_remaining"));
                    set(prisoner, "hits", convertStringToCharSet(rs.getString("hits")));
                    set(prisoner, "misses", convertStringToCharSet(rs.getString("misses")));
                    Game game = new Game(gameId, prisoner);
                    return Optional.of(game);
                }
            };
            return new QueryRunner().query(connection, "select * from hangman_games where game_id = ?", handler, gameId);

        }
    }

    @SneakyThrows
    private void set(Object target, String fieldName, Object value) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    public Set<String> convertStringToCharSet(String dbData) {
        if (dbData.isEmpty())
            return new HashSet<>();
        return new HashSet<String>(stream(dbData.split("")).collect(toSet()));
    }

}
