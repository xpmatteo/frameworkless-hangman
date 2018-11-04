package it.xpug.frameworkless.hangman.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@ToString
@Getter
public class Prisoner {

	private String word;

	private int guessesRemaining = 18;

	private Set<String> misses = new HashSet<>();

	private Set<String> hits = new HashSet<>();

	public Prisoner() {
		this(new WordList().getRandomWord());
	}

	public Prisoner(String word) {
		this.word = word;
	}

	public String getState() {
		if (word.equals(getMaskedWord() ))
			return "rescued";
		if (guessesRemaining > 0)
			return "help";
		return "lost";
	}

	public String getMaskedWord() {
		String result = "";
		for (int i=0; i<word.length(); i++) {
			String c = word.substring(i, i+1);
			if (hits.contains(c)) {
				result += c;
			} else {
				result += "*";
			}
		}
		return result;
	}

	public void guess(String guess) {
		if (guessesRemaining == 0) {
			return;
		}
		this.guessesRemaining--;
		if (word.contains(guess)) {
			hits.add(guess);
		} else {
			misses.add(guess);
		}
	}

	public int getGuessesRemaining() {
		return guessesRemaining;
	}
}
