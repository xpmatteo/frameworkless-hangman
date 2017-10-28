package it.xpug.unsprung.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import it.xpug.unsprung.util.ToHexSerializer;
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
    @JsonSerialize(using=ToHexSerializer.class)
    private Long gameId;
}
