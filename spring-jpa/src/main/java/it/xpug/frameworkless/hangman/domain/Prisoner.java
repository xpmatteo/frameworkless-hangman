package it.xpug.frameworkless.hangman.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.frameworkless.hangman.util.SetConverter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;

@Embeddable
@EqualsAndHashCode
@ToString
public class Prisoner {

	private String word;

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
