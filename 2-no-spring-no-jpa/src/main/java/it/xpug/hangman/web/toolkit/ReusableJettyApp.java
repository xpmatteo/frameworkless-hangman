package it.xpug.hangman.web.toolkit;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.http.HttpServlet;


public class ReusableJettyApp {

    private Server server;
    private final Class<? extends HttpServlet> servletClass;

    public ReusableJettyApp(Class<? extends HttpServlet> servlet) {
        this.servletClass = servlet;
    }

    public void start(int port) {
        server = new Server(port);
        server.setRequestLog(new Slf4jRequestLog());
        try {
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] {
                    servletHandler(),
                    new DefaultHandler()
            });
            server.setHandler(handlers);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ServletHandler servletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(servletClass, "/");
        return servletHandler;
    }

    public void shutdown() throws InterruptedException {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
