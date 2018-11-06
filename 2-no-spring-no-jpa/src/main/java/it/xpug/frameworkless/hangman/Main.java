package it.xpug.frameworkless.hangman;


import it.xpug.frameworkless.hangman.web.toolkit.EmbeddedJetty;

public class Main {

	public static void main(String[] args) {
		EmbeddedJetty embeddedJetty = new EmbeddedJetty(HangmanServlet.class);
		embeddedJetty.start(8080);
	}
}
