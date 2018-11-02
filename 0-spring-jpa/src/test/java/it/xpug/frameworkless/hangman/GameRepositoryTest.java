package it.xpug.frameworkless.hangman;

import com.mysql.cj.jdbc.MysqlDataSource;
import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.domain.Game;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.Prisoner;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigInteger;
import java.util.Optional;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameRepositoryTest {

    EntityManager entityManager = createTestEntityManager();

    GameIdGenerator gameIdGenerator = mock(GameIdGenerator.class);

    GameRepository gameRepository = new GameRepository(gameIdGenerator, entityManager);

    EntityTransaction transaction;

    @Before
    public void setUp() {
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createNativeQuery("delete from hangman_games").executeUpdate();
    }

    @After
    public void tearDown() throws Exception {
        transaction.commit();
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
    public void createNewGameWithGivenWord() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn(33L);
        assertThat(gameCount(), is(0));

        Game game = gameRepository.createNewGame("aha");

        assertThat(game.getGameId(), is(33L));
        assertThat(game.getPrisoner().getWord(), is("aha"));
        assertThat(gameCount(), is(1));
    }

    @Test
    public void findGame() throws Exception {
        when(gameIdGenerator.generateGameId()).thenReturn(789L);
        gameRepository.createNewGame();

        Optional<Game> game = gameRepository.findGame(789L);

        assertThat("not present", game.isPresent(), is(true));
        assertThat(game.get().getGameId(), is(789L));
        assertThat(game.get().getPrisoner().getGuessesRemaining(), is(18));
    }

    @Test
    public void saveAndLoad() throws Exception {
        Game original = new Game(42L, new Prisoner("foobar"));
        original.getPrisoner().guess("x");
        original.getPrisoner().guess("y");
        original.getPrisoner().guess("f");
        original.getPrisoner().guess("o");
        gameRepository.save(original);

        Optional<Game> game = gameRepository.findGame(42L);

        assertThat("not present", game.isPresent(), is(true));
        assertThat(game.get().getPrisoner(), is(original.getPrisoner()));
    }

    @Test
    public void gameNotFound() throws Exception {
        Optional<Game> game = gameRepository.findGame(9869L);

        assertThat("not present", game.isPresent(), is(false));
    }

    private int gameCount() {
        String sql = "select count(*) from hangman_games";
        return Integer.valueOf(((BigInteger) select(sql)).toString());
    }

    private Object select(String sql) {
        return entityManager.createNativeQuery(sql).getSingleResult();
    }

    // does not work!!!
    // https://stackoverflow.com/a/26814642/164802
    public EntityManager createTestEntityManager() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/hangman_test?serverTimezone=UTC");
        dataSource.setUser("hangman");
        dataSource.setPassword("hangman");

        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("it.xpug.frameworkless.hangman.domain");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties);
        em.setPersistenceUnitName("default");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();

        return em.getObject().createEntityManager();
    }

}