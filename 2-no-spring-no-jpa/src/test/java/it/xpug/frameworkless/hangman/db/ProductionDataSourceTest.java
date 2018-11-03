package it.xpug.frameworkless.hangman.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProductionDataSourceTest {

    @Test
    public void canChooseWhichDatabase() throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/hangman_test?user=hangman&password=hangman";
        ProductionDataSource dataSource = new ProductionDataSource(url);

        String result = new QueryRunner(dataSource).query("select database()", new ScalarHandler<>());

        assertThat(result, is("hangman_test"));
    }
}