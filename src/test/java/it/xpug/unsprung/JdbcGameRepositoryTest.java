package it.xpug.unsprung;

import it.xpug.unsprung.domain.Game;
import it.xpug.unsprung.domain.GameIdGenerator;
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

import java.math.BigInteger;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class JdbcGameRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @MockBean
    GameIdGenerator gameIdGenerator;

    @Autowired
    JdbcGameRepository jdbcGameRepository;

    @Before
    public void setUp() {
        entityManager.createNativeQuery("delete from hangout_games").executeUpdate();
    }

    @Test
    public void createNewGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn("123");
        assertThat(gameCount(), is(0));

        Game game = jdbcGameRepository.createNewGame();

        assertThat(game.getGameId(), is("123"));
        assertThat(gameCount(), is(1));
    }

    private int gameCount() {
        String sql = "select count(*) from hangout_games";
        BigInteger result = (BigInteger) entityManager.createNativeQuery(sql).getSingleResult();
        return Integer.valueOf(result.toString());
    }


}