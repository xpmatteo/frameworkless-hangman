package it.xpug.frameworkless.hangman;

import it.xpug.frameworkless.hangman.service.HangmanService;
import it.xpug.frameworkless.hangman.db.GameRepository;
import it.xpug.frameworkless.hangman.db.ProductionDataSource;
import it.xpug.frameworkless.hangman.domain.RandomGameIdGenerator;
import it.xpug.frameworkless.hangman.web.HangmanRouter;
import it.xpug.frameworkless.hangman.web.WebRequest;
import it.xpug.frameworkless.hangman.web.WebResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HangmanServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GameRepository gameRepository = new GameRepository(new RandomGameIdGenerator(), new ProductionDataSource());
        HangmanService hangmanService = new HangmanService(gameRepository);
        WebRequest webRequest = new WebRequest(request);
        WebResponse webResponse = new WebResponse(response);
        HangmanRouter hangmanRouter = new HangmanRouter(webRequest, webResponse, hangmanService);

        hangmanRouter.route();
    }
}
