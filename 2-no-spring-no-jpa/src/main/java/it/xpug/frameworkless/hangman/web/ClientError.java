package it.xpug.frameworkless.hangman.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientError extends RuntimeException {
    private final int status;
    private final String message;
}
