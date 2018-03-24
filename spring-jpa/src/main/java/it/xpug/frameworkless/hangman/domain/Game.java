package it.xpug.frameworkless.hangman.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.frameworkless.hangman.util.ToHexSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hangman_games")
@NoArgsConstructor
public class Game {
    @Id
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
