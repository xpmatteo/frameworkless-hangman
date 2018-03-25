package it.xpug.frameworkless.hangman.domain;

import it.xpug.frameworkless.hangman.util.SetConverter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

@Embeddable
@EqualsAndHashCode
@ToString
public class Prisoner {

	private String word;

	@Column(name = "guesses_remaining")
	private int guessesRemaining = 18;

    @Convert(converter = SetConverter.class)
	private Set<String> misses = new HashSet<String>();

    @Convert(converter = SetConverter.class)
	private Set<String> hits = new HashSet<String>();

	public Prisoner() {
		this(new WordList().getRandomWord());
	}

	public Prisoner(String word) {
		this.word = word;
	}

	public Set<String> getMisses() {
		return unmodifiableSet(misses);
	}

	public Set<String> getHits() {
		return unmodifiableSet(hits);
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

	public int getGuessesRemaining() {
		return guessesRemaining;
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

}
