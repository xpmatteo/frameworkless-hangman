package it.xpug.unsprung.hangman.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.xpug.unsprung.hangman.util.ToHexSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "hangman_games")
@NoArgsConstructor
public class Game {
    @Id
    @Getter
    @JsonSerialize(using=ToHexSerializer.class)
    private Long gameId;

    @Override
    public String toString() {
        return "Game " + gameId;
    }

    @Transient
    private Prisoner prisoner;

    public Game(Long gameId) {
        this.gameId = gameId;
    }
}
