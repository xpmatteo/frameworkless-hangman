package it.xpug.unsprung.hangman;

import it.xpug.unsprung.hangman.domain.Game;
import it.xpug.unsprung.hangman.domain.Prisoner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HangmanController.class)
public class HangmanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void createNewGame() throws Exception {
        when(gameRepository.createNewGame()).thenReturn(new Game(123L));

        mockMvc.perform(post("/hangman/game"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("gameId", is("7b")))
        ;
    }

    @Test
    public void findGame() throws Exception {
        Game game = new Game(255L);
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(get("/hangman/game/ff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("ff")))
                .andExpect(jsonPath("prisoner.guesses_remaining", is(18)))
                .andExpect(jsonPath("prisoner.hits", is(emptyList())))
                .andExpect(jsonPath("prisoner.misses", is(emptyList())))
        ;
    }

    @Test
    public void cannotFindGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/hangman/game/777"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void guess() throws Exception {
        Game game = new Game(255L, new Prisoner("pippo"));
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(post("/hangman/game/ff/guesses").param("guess", "x")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("gameId", is("ff")))
                .andExpect(jsonPath("prisoner.guesses_remaining", is(17)))
                .andExpect(jsonPath("prisoner.hits", is(emptyList())))
                .andExpect(jsonPath("prisoner.misses", is(singletonList("x"))))
        ;

        // we have no good way to test that the game was saved!!!
        // in fact it is saved because the controller is annotated as "transactional"
    }

    @Test
    public void guessOnInexistentGame() throws Exception {
        when(gameRepository.findGame(any())).thenReturn(Optional.empty());

        mockMvc.perform(
                post("/hangman/game/ff/guesses")
                .param("guess", "a")
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Game with id 'ff' not found")))
                .andExpect(jsonPath("status", is(404)))
        ;
    }

    @Test
    public void guessTooLongWord() throws Exception {
        Game game = new Game(255L, new Prisoner("pippo"));
        when(gameRepository.findGame(255L)).thenReturn(Optional.of(game));

        mockMvc.perform(
                post("/hangman/game/ff/guesses")
                        .param("guess", "xxxx")
        )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Guess 'xxxx' invalid: must be a single letter")))
                .andExpect(jsonPath("status", is(400)))
        ;
    }
}
