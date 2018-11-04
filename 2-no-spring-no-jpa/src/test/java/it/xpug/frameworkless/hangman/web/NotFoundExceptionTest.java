package it.xpug.frameworkless.hangman.web;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class NotFoundExceptionTest {

    @Test
    public void message() throws Exception {
        assertThat(new NotFoundException("/a/b/c").getMessage(), is("Not found: /a/b/c"));
    }
}