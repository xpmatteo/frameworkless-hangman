package it.xpug.frameworkless.hangman.web;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption(String path) {
    }
}
