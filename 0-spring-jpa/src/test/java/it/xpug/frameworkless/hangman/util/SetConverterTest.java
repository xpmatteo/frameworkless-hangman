package it.xpug.frameworkless.hangman.util;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SetConverterTest {

    @Test
    public void fromNonEmptyString() throws Exception {
        Set<String> set = new SetConverter().convertToEntityAttribute("abcd");
        assertThat(set, is(new HashSet<>(asList("a", "b", "c", "d"))));
    }

    @Test
    public void fromEmptyString() throws Exception {
        Set<String> set = new SetConverter().convertToEntityAttribute("");
        assertThat(set.size(), is(0));
    }

    @Test
    public void returnsMutableSet() throws Exception {
        Set<String> emptySet = new SetConverter().convertToEntityAttribute("");
        emptySet.add("x");
        // expect nothing breaks
        Set<String> nonEmptySet = new SetConverter().convertToEntityAttribute("abc");
        nonEmptySet.add("x");
    }

}