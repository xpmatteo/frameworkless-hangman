package it.xpug.frameworkless.hangman.db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestDataSource extends MysqlDataSource {
    public TestDataSource() {
        setDatabaseName("hangman_test");
        setUser("hangman");
        setPassword("hangman");
    }
}
