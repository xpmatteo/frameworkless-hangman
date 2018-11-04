package it.xpug.frameworkless.hangman.web.toolkit;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NotFoundException extends ClientError {
    public NotFoundException(String path) {
        super(404, "Not found: " + path);
    }
}
