package it.xpug.unsprung.hangman.domain;

import java.util.*;


public class Prisoner {

	private String id;
	private String word;
	private int guessesRemaining = 18;
	private Set<String> misses = new HashSet<String>();
	private Set<String> hits = new HashSet<String>();

	public Prisoner(String id) {
		this(id, new WordList().getRandomWord());
	}

	public Prisoner(String id, String word) {
		this.id = id;
		this.word = word;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("word", getMaskedWord());
		result.put("guesses_remaining", guessesRemaining);
		result.put("misses", misses);
		result.put("hits", hits);
		result.put("state", state());
		result.put("id", id);
		return result;
	}

	private String state() {
		if (word.equals(getMaskedWord() ))
			return "rescued";
		if (guessesRemaining > 0)
			return "help";
		return "lost";
	}

	private String getMaskedWord() {
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

	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Prisoner " + id;
	}

	public int getGuessesRemaining() {
		return guessesRemaining;
	}
}
