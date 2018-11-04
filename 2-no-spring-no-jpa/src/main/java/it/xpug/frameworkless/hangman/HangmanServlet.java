package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.db.ProductionDataSource;
import it.xpug.frameworkless.hangman.domain.RandomGameIdGenerator;
import it.xpug.frameworkless.hangman.web.HangmanController;
import it.xpug.frameworkless.hangman.web.toolkit.WebRequest;
import it.xpug.frameworkless.hangman.web.toolkit.WebResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HangmanServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GameRepository gameRepository = new GameRepository(new RandomGameIdGenerator(), new ProductionDataSource());
        HangmanController hangmanController = new HangmanController(gameRepository);
        HangmanRouter hangmanRouter = new HangmanRouter(hangmanController);

        hangmanRouter.route(new WebRequest(request), new WebResponse(response));
    }
}
