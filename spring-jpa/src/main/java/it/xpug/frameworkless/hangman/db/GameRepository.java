package it.xpug.frameworkless.hangman.db;

import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class GameRepository {
    private GameIdGenerator gameIdGenerator;
    private EntityManager entityManager;

    @Autowired
    public GameRepository(GameIdGenerator gameIdGenerator, EntityManager entityManager) {
        this.gameIdGenerator = gameIdGenerator;
        this.entityManager = entityManager;
    }

    public Game createNewGame() {
        Game newGame = new Game(gameIdGenerator.generateGameId(), new Prisoner());
        return save(newGame);
    }

    public Game save(Game newGame) {
        entityManager.merge(newGame);
        return newGame;
    }

    public Optional<Game> findGame(Long gameId) {
        Game found = entityManager.find(Game.class, gameId);
        if (null == found)
            return Optional.empty();
        return Optional.of(found);
    }
}
