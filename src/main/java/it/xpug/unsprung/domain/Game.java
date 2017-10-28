package it.xpug.unsprung.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "hangout_games")
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @Getter
    private String gameId;
}
