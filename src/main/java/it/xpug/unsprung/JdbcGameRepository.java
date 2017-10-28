package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import it.xpug.unsprung.domain.GameIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JdbcGameRepository implements GameRepository {
    private GameIdGenerator gameIdGenerator;

    @Autowired
    public JdbcGameRepository(GameIdGenerator gameIdGenerator) {
        this.gameIdGenerator = gameIdGenerator;
    }

    @Override
    public Game createNewGame() {
        return new Game(gameIdGenerator.generateGameId());
    }

    @Override
    public Optional<Game> findGame(String gameId) {
        return null;
    }
}
