package it.xpug.frameworkless.hangman;


import it.xpug.hangman.web.toolkit.ReusableJettyApp;

public class HangmanApplication {

	public static void main(String[] args) {
		ReusableJettyApp app = new ReusableJettyApp(HangmanServlet.class);
		app.start(8080, "src/main/webapp");
	}
}
