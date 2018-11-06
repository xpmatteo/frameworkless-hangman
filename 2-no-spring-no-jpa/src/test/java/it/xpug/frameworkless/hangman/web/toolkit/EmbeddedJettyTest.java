package it.xpug.simplewebapp.jetty;

import it.xpug.frameworkless.hangman.web.toolkit.EmbeddedJetty;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class EmbeddedJettyTest {

	private static EmbeddedJetty embeddedJetty = new EmbeddedJetty(TestServlet.class);
	
	public static class TestServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.getWriter().write("Hello!");
		}
	}

	@Test
	public void servletIsInvokedOnRoot() throws Exception {
		assertThat(get("/"), startsWith("Hello!"));
	}
	
	@Test
	public void servletIsInvokedOnAnyArbitraryPath() throws Exception {
		assertThat(get("/pippo"), startsWith("Hello!"));
	}

	@BeforeClass
	public static void setUp() throws Exception {
		embeddedJetty.start(8123);
	}

	@AfterClass
	public static void tearDown() throws Exception {
		embeddedJetty.shutdown();
	}

	private String get(String path) throws IOException {
		InputStream stream = new URL("http://localhost:" + 8123 + path).openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.readLine();
	}
}
