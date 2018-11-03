package it.xpug.frameworkless.hangman.db;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ProductionDataSource extends MysqlDataSource {

    public static final String LOCAL_DEVELOPMENT_DATABASE = "jdbc:mysql://127.0.0.1:3306/hangman?user=hangman&password=hangman";
    public static final String ENV_VARIABLE_NAME = "DATABASE_URL";

    public ProductionDataSource() {
        this(findUrl());
    }

    ProductionDataSource(String url) {
        setURL(url);
    }

    private static String findUrl() {
        String url = System.getenv(ENV_VARIABLE_NAME);
        return url == null ? LOCAL_DEVELOPMENT_DATABASE : url;
    }
}
