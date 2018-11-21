package it.xpug.frameworkless.hangman.db;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Guess;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import it.xpug.frameworkless.hangman.web.GuessRequest;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class GameRepository {
    private GameIdGenerator gameIdGenerator;
    private DataSource dataSource;

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
                    prisoner.getWord(),
                    prisoner.getGuessesRemaining(),
                    convertCharSetToString(prisoner.getHits()),
                    convertCharSetToString(prisoner.getMisses())
            );
        }
        return game;
    }

    @SneakyThrows
    public Optional<Game> findGame(Long gameId) {
        try (Connection connection = dataSource.getConnection()) {
            Optional<Game> maybeGame = loadGame(gameId, connection);
            if (!maybeGame.isPresent())
                return Optional.empty();
            Game game = maybeGame.get();
            List<Guess> guesses = loadGuesses(gameId, connection);
            guesses.forEach(guess -> {
                game.getPrisoner().guess(guess);
            });
            return Optional.of(game);
        }
    }

    @SneakyThrows
    List<Guess> loadGuesses(Long gameId, Connection connection) {
        String sql = "select * from guesses where game_id = ?";
        ResultSetHandler<List<Guess>> handler = rs -> {
            List<Guess> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Guess(rs.getString("letter")));
            }
            return result;
        };
        return new QueryRunner().query(connection, sql, handler, gameId);
    }

    private Optional<Game> loadGame(Long gameId, Connection connection) throws java.sql.SQLException {
        String sql = "select * from hangman_games  where game_id = ?";
        ResultSetHandler<Optional<Game>> handler = rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Prisoner prisoner = new Prisoner(rs.getString("word"));
            set(prisoner, "guessesRemaining", rs.getObject("guesses_remaining"));
            Game game = new Game(gameId, prisoner);
            return Optional.of(game);
        };
        return new QueryRunner().query(connection, sql, handler, gameId);
    }

    @SneakyThrows
    private void set(Object target, String fieldName, Object value) {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Set<Guess> convertStringToCharSet(String dbData) {
        if (dbData.isEmpty())
            return new HashSet<>();
        return stream(dbData.split("")).map(Guess::new).collect(toSet());
    }

    private String convertCharSetToString(Set<Guess> set) {
        return set.stream().map(Guess::getLetter).sorted().collect(joining());
    }

    @SneakyThrows
    public void save(GuessRequest guessRequest) {
        String sql = "" +
                "insert into guesses " +
                " (game_id, letter, ip_address, forwarded_for) " +
                " values " +
                " (?, ?, ?, ?) ";
        try (Connection connection = dataSource.getConnection()) {
            new QueryRunner().execute(
                    connection,
                    sql,
                    guessRequest.getGameId(),
                    guessRequest.getGuess().getLetter(),
                    guessRequest.getIpAddress(),
                    guessRequest.getForwardedFor()
            );
        }
    }
}
