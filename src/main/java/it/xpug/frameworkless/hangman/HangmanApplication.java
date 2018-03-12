package it.xpug.frameworkless.hangman;

import com.mysql.cj.jdbc.MysqlDataSource;
import it.xpug.frameworkless.hangman.domain.GameIdGenerator;
import it.xpug.frameworkless.hangman.domain.RandomGameIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

//@SpringBootApplication
@Configuration
@EnableWebMvc
//@ComponentScan
//@EnableAutoConfiguration
public class HangmanApplication {

    @Bean
    TomcatEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        return new TomcatEmbeddedServletContainerFactory();
    }

    @Bean
    DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}")String password)
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name="/")
    HangmanController hangmanController(GameRepository gameRepository) {
        return new HangmanController(gameRepository);
    }

    @Bean
    GameRepository gameRepository(GameIdGenerator gameIdGenerator, DataSource dataSource) {
        return new GameRepository(gameIdGenerator, dataSource);
    }

    @Bean
    GameIdGenerator gameIdGenerator() {
        return new RandomGameIdGenerator() ;
    }

    public static void main(String[] args) {
		SpringApplication.run(HangmanApplication.class, args);
	}

}
