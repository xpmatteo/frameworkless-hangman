package it.xpug.frameworkless.hangman.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hangman_games")
@NoArgsConstructor
@Getter
public class Game {
    @Id
    private Long gameId;

    private Prisoner prisoner;

    public Game(Long gameId) {
        this(gameId, new Prisoner());
    }

    public Game(Long gameId, Prisoner prisoner) {
        this.gameId = gameId;
        this.prisoner = prisoner;
    }

    @Override
    public String toString() {
        return "Game " + gameId;
    }
}
