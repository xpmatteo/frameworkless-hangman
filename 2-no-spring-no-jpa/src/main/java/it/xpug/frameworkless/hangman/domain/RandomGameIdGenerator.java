package it.xpug.frameworkless.hangman.domain;


import java.util.Random;

import static java.lang.Math.abs;

public class RandomGameIdGenerator implements GameIdGenerator {
    @Override
    public Long generateGameId() {
        return abs(new Random().nextLong());
    }
}
