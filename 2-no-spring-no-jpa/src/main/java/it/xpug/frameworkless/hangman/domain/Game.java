package it.xpug.frameworkless.hangman.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Game {
    @Getter
    private Long gameId;

    @Override
    public String toString() {
        return "Game " + gameId;
    }

    @Getter@Setter
    private Prisoner prisoner;

    public Game(Long gameId) {
        this(gameId, new Prisoner());
    }

    public Game(Long gameId, Prisoner prisoner) {
        this.gameId = gameId;
        this.prisoner = prisoner;
    }

}
