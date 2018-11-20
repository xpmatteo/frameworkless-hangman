package it.xpug.frameworkless.hangman.web;

import lombok.EqualsAndHashCode;

public class NotFoundException extends ClientError {
    public NotFoundException(String path) {
        super(404, "Not found: " + path);
    }
}
