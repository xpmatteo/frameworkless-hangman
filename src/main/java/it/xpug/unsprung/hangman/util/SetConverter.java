package it.xpug.unsprung.hangman.util;

import javax.persistence.AttributeConverter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

public class SetConverter implements AttributeConverter<Set<String>, String> {
    @Override
    public String convertToDatabaseColumn(Set<String> set) {
        return set.stream().sorted().collect(joining());
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if (dbData.isEmpty())
            return new HashSet<>();
        return new HashSet<String>(stream(dbData.split("")).collect(toSet()));
    }
}
