package it.xpug.unsprung.hangman.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
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
}