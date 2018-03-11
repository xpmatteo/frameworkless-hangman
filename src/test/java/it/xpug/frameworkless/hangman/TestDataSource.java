package it.xpug.frameworkless.hangman;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class TestDataSource extends MysqlDataSource {
    public TestDataSource() {
        setDatabaseName("hangman_test");
        setUser("hangman");
        setPassword("hangman");
    }
}
