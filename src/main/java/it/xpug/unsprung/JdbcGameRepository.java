package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import it.xpug.unsprung.domain.GameIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JdbcGameRepository implements GameRepository {
    private GameIdGenerator gameIdGenerator;
    private HangoutTable hangoutTable;

    @Autowired
    public JdbcGameRepository(GameIdGenerator gameIdGenerator, HangoutTable hangoutTable) {
        this.gameIdGenerator = gameIdGenerator;
        this.hangoutTable = hangoutTable;
    }

    @Override
    public Game createNewGame() {
        Game newGame = new Game(gameIdGenerator.generateGameId());
        hangoutTable.save(newGame);
        return newGame;
    }

    @Override
    public Optional<Game> findGame(Long gameId) {
        Game found = hangoutTable.findOne(gameId);
        if (null == found)
            return Optional.empty();
        return Optional.of(found);
    }
}
