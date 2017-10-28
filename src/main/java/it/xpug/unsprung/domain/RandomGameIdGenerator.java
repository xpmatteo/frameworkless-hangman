package it.xpug.unsprung.domain;

import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.Math.abs;

@Component
public class RandomGameIdGenerator implements GameIdGenerator {
    @Override
    public String generateGameId() {
        return Long.toHexString(abs(new Random().nextLong()));
    }
}
