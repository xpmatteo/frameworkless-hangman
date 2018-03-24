package it.xpug.frameworkless.hangman.db;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameRepository {
    private GameIdGenerator gameIdGenerator;
    private HangoutTable hangoutTable;

    @Autowired
    public GameRepository(GameIdGenerator gameIdGenerator, HangoutTable hangoutTable) {
        this.gameIdGenerator = gameIdGenerator;
        this.hangoutTable = hangoutTable;
    }

    public Game createNewGame() {
        Game newGame = new Game(gameIdGenerator.generateGameId(), new Prisoner());
        return save(newGame);
    }

    public Game save(Game newGame) {
        hangoutTable.save(newGame);
        return newGame;
    }

    public Optional<Game> findGame(Long gameId) {
        Game found = hangoutTable.findOne(gameId);
        if (null == found)
            return Optional.empty();
        return Optional.of(found);
    }
}
