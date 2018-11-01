package it.xpug.frameworkless.hangman.db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.stereotype.Component;

@Component
public class HangmanDataSource extends MysqlDataSource {
    public HangmanDataSource() {
        setDatabaseName("hangman");
        setUser("hangman");
        setPassword("hangman");
    }
}
