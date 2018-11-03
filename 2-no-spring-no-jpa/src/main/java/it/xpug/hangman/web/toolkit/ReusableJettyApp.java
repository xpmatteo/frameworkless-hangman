package it.xpug.hangman.web.toolkit;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ReusableJettyApp {

    private Server server;
    private final Class<? extends HttpServlet> servletClass;

    public ReusableJettyApp(Class<? extends HttpServlet> servlet) {
        this.servletClass = servlet;
    }

    public void start(int port, String resourceBase) {
        server = new Server(port);
        server.setRequestLog(new Slf4jRequestLog());
        try {
            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[] {
                    resourceHandler(resourceBase),
                    servletHandler(),
                    new DefaultHandler()
            });
            server.setHandler(handlers);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ResourceHandler resourceHandler(String resourceBase) {
        ResourceHandler resourceHandler = new ResourceHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                // when the request is for "/", then it should NOT be handled here
                // otherwise we'd return 403
                if (request.getPathInfo().equals("/")) {
                    ((Request) request).setHandled(false);
                } else {
                    super.handle(target, baseRequest, request, response);
                }
            }
        };
        resourceHandler.setResourceBase(resourceBase);
        resourceHandler.setDirAllowed(false);
        return resourceHandler;
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
