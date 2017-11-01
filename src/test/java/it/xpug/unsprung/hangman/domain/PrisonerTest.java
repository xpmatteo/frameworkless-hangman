package it.xpug.unsprung.hangman.domain;

import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PrisonerTest {
	Prisoner prisoner = new Prisoner("someword");

	@Test
	public void newPrisonerToJSON() {
		assertEquals("********", get("word"));
		assertEquals(18, get("guesses_remaining"));
		assertEquals("help", get("state"));
	}

	@Test
	public void randomWord() throws Exception {
		WordList list = new WordList(new Random(123));
		assertEquals("handhold", list.getRandomWord());
	}

	@Test
	public void miss() throws Exception {
		prisoner.guess("a");
		assertEquals(17, get("guesses_remaining"));
		assertEquals(set("a"), get("misses"));
		assertEquals(set(), get("hits"));
		assertEquals("********", get("word"));
	}

	@Test
	public void hit() throws Exception {
		prisoner.guess("o");
		assertEquals(17, get("guesses_remaining"));
		assertEquals(set(), get("misses"));
		assertEquals(set("o"), get("hits"));
		assertEquals("*o***o**", get("word"));
	}

	@Test
	public void lose() throws Exception {
		miss18times();
		assertEquals("lost", get("state"));
	}

	private void miss18times() {
		for (int i = 0; i < 18; i++) {
			prisoner.guess("a");
		}
	}

	@Test
	public void win() throws Exception {
		prisoner.guess("s");
		prisoner.guess("o");
		prisoner.guess("m");
		prisoner.guess("e");
		prisoner.guess("w");
		prisoner.guess("r");
		prisoner.guess("d");
		assertEquals("rescued", get("state"));
	}

	@Test
	public void noFurtherGuesses() throws Exception {
		miss18times();
		prisoner.guess("x");
		assertEquals(set("a"), get("misses"));
		assertEquals(0, get("guesses_remaining"));
	}

    private Set<String> set(String ... strings) {
		Set<String> result = new HashSet<String>();
		for (String string : strings) {
			result.add(string);
		}
		return result;
	}

	private Object get(String key) {
		return prisoner.toMap().get(key);
	}
}
