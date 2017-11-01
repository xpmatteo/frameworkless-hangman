package it.xpug.unsprung.hangman.util;

import javax.persistence.AttributeConverter;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class SetConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        return set.stream().sorted().collect(joining());
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return stream(dbData.split("")).collect(toSet());
    }
}
