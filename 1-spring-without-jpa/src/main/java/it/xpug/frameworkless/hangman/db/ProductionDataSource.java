package it.xpug.frameworkless.hangman.db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductionDataSource extends MysqlDataSource {

    // The actual values are set in the application.properties file
    public ProductionDataSource(
            @Value("${datasource.url}") String url,
            @Value("${datasource.username}") String username,
            @Value("${datasource.password}") String password)
    {
        setURL(url);
        setUser(username);
        setPassword(password);
    }
}
