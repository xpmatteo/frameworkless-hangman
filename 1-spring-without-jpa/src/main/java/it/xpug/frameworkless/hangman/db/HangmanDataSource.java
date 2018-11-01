package it.xpug.frameworkless.hangman.db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HangmanDataSource extends MysqlDataSource {

    // The actual values are set in the application.properties file
    public HangmanDataSource(
            @Value("${datasource.dbname}")   String dbname,
            @Value("${datasource.username}") String username,
            @Value("${datasource.password}") String password)
    {
        setDatabaseName(dbname);
        setUser(username);
        setPassword(password);
    }
}
