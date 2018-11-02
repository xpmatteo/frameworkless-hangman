package it.xpug.frameworkless.hangman.db;

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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

@Service
public class GameRepository {
    private GameIdGenerator gameIdGenerator;
    private DataSource dataSource;

    @Autowired
    public GameRepository(GameIdGenerator gameIdGenerator, DataSource dataSource) {
        this.gameIdGenerator = gameIdGenerator;
        this.dataSource = dataSource;
    }

    public Game createNewGame() {
        Game newGame = new Game(gameIdGenerator.generateGameId(), new Prisoner());
        return create(newGame);
    }

    public Game createNewGame(String word) {
        Game newGame = new Game(gameIdGenerator.generateGameId(), new Prisoner(word));
        return create(newGame);
    }

    @SneakyThrows
    public Game create(Game game) {
        String sql = "insert into hangman_games " +
                "(game_id, word, guesses_remaining, hits, misses) " +
                "values" +
                "(?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            Prisoner prisoner = game.getPrisoner();
            new QueryRunner().execute(
                    connection,
                    sql,
                    game.getGameId(),
                    get(prisoner, "word"),
                    get(prisoner, "guessesRemaining"),
                    convertCharSetToString((Set<String>) get(prisoner, "hits")),
                    convertCharSetToString((Set<String>) get(prisoner, "misses"))
            );
        }
        return game;
    }

    @SneakyThrows
    public void update(Game game) {
        String sql = "update hangman_games set " +
                "word = ?, " +
                "guesses_remaining = ?, " +
                "hits = ?, " +
                "misses = ? " +
                "where game_id = ? ";
        try (Connection connection = dataSource.getConnection()) {
            Prisoner prisoner = game.getPrisoner();
            new QueryRunner().execute(
                    connection,
                    sql,
                    get(prisoner, "word"),
                    get(prisoner, "guessesRemaining"),
                    convertCharSetToString((Set<String>) get(prisoner, "hits")),
                    convertCharSetToString((Set<String>) get(prisoner, "misses")),
                    game.getGameId()
            );
        }
    }

    @SneakyThrows
    public Optional<Game> findGame(Long gameId) {
        String sql = "select * from hangman_games where game_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            ResultSetHandler<Optional<Game>> handler = rs -> {
                if (!rs.next()) {
                    return Optional.empty();
                }

                Prisoner prisoner = new Prisoner(rs.getString("word"));
                set(prisoner, "guessesRemaining", rs.getObject("guesses_remaining"));
                set(prisoner, "hits", convertStringToCharSet(rs.getString("hits")));
                set(prisoner, "misses", convertStringToCharSet(rs.getString("misses")));
                Game game = new Game(gameId, prisoner);
                return Optional.of(game);
            };
            return new QueryRunner().query(connection, sql, handler, gameId);
        }
    }

    @SneakyThrows
    private void set(Object target, String fieldName, Object value) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @SneakyThrows
    private Object get(Object object, String fieldName) {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private Set<String> convertStringToCharSet(String dbData) {
        if (dbData.isEmpty())
            return new HashSet<>();
        return stream(dbData.split("")).collect(toSet());
    }

    private String convertCharSetToString(Set<String> set) {
        return set.stream().sorted().collect(joining());
    }
}
