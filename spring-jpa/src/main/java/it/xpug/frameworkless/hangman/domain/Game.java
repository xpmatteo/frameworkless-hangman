package it.xpug.frameworkless.hangman.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hangman_games")
@NoArgsConstructor
public class Game {
    @Id
    @Getter
    @Column(name = "game_id")
    private Long gameId;

    @Override
    public String toString() {
        return "Game " + gameId;
    }

    @Getter
    private Prisoner prisoner;

    public Game(Long gameId) {
        this(gameId, new Prisoner());
    }

    public Game(Long gameId, Prisoner prisoner) {
        this.gameId = gameId;
        this.prisoner = prisoner;
    }

}
