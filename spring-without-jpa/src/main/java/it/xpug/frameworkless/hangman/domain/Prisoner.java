package it.xpug.frameworkless.hangman.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@ToString
@JsonSerialize(using = Prisoner.JsonSerializer.class)
public class Prisoner {

	private String word;

	private int guessesRemaining = 18;

	private Set<String> misses = new HashSet<String>();

	private Set<String> hits = new HashSet<String>();

	public Prisoner() {
		this(new WordList().getRandomWord());
	}

	public Prisoner(String word) {
		this.word = word;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("word", getMaskedWord());
		result.put("guesses_remaining", guessesRemaining);
		result.put("misses", misses);
		result.put("hits", hits);
		result.put("state", state());
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

	public int getGuessesRemaining() {
		return guessesRemaining;
	}

    public static class JsonSerializer extends com.fasterxml.jackson.databind.JsonSerializer {
        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            Prisoner prisoner = (Prisoner) value;
            gen.writeObject(prisoner.toMap());
        }
    }
}
